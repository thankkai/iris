/*
 * Copyright (c) 2015 Uber Technologies, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.dazd.iris.core.router;

import static com.uber.tchannel.frames.ErrorFrame.sendError;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.errors.ErrorType;
import com.uber.tchannel.messages.Request;
import com.uber.tchannel.messages.Response;
import com.uber.tchannel.messages.ResponseMessage;
import com.uber.tchannel.tracing.Tracing;

import cn.dazd.iris.core.tchannel.handler.GatewayHandlerImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.opentracing.Span;

/**
 * 自定义请求路由类，仅在开启网关注解时生效
 * 
 * @author Administrator
 *
 */
@Sharable
public class RequestRouter extends SimpleChannelInboundHandler<Request> {

	private static final Logger logger = LoggerFactory.getLogger(RequestRouter.class);

	private final TChannel topChannel;

	private final ListeningExecutorService listeningExecutorService;

	private final AtomicBoolean busy = new AtomicBoolean(false);
	private final ConcurrentLinkedQueue<Response> responseQueue = new ConcurrentLinkedQueue<>();

	public RequestRouter(TChannel topChannel, ExecutorService executorService) {
		this.topChannel = topChannel;
		this.listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final Request request) {

		if (!ctx.channel().isActive()) {
			logger.warn("drop request when channel is inActive");
			request.release();
			return;
		}

		final String service = request.getService();
		if (service == null || service.isEmpty()) {
			sendError(ErrorType.BadRequest, "Expected incoming call to have serviceName", request, ctx);
			return;
		}

		String endpoint = request.getArg1().toString(CharsetUtil.UTF_8);
		if (endpoint == null || endpoint.isEmpty()) {
			sendError(ErrorType.BadRequest, "Expected incoming call to have endpoint", request, ctx);
			return;
		}

		ListenableFuture<Response> responseFuture = listeningExecutorService
				.submit(new CallableHandler(topChannel, request));

		Futures.addCallback(responseFuture, new FutureCallback<Response>() {
			@Override
			public void onSuccess(Response response) {
				if (!ctx.channel().isActive()) {
					response.release();
					return;
				}

				responseQueue.offer(response);
				sendResponse(ctx);
			}

			@Override
			public void onFailure(Throwable throwable) {
				logger.error("Failed to handle the request due to exception.", throwable);
				sendError(ErrorType.UnexpectedError, "Failed to handle the request: " + throwable.getMessage(), request,
						ctx);
				return;
			}
		});
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) {
		sendResponse(ctx);
	}

	protected void sendResponse(ChannelHandlerContext ctx) {

		if (!busy.compareAndSet(false, true)) {
			return;
		}

		Channel channel = ctx.channel();
		try {
			boolean flush = false;
			while (channel.isWritable()) {
				Response res = responseQueue.poll();
				if (res == null) {
					break;
				}

				channel.write(res);
				flush = true;
			}

			if (flush) {
				channel.flush();
			}
		} finally {
			busy.set(false);
		}

		if (channel.isWritable() && !responseQueue.isEmpty()) {
			sendResponse(ctx);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);

		while (!responseQueue.isEmpty()) {
			ResponseMessage res = responseQueue.poll();
			res.release();
		}
	}

	private class CallableHandler implements Callable<Response> {
		private final Request request;
		private final TChannel topChannel;
		private final GatewayHandlerImpl handler = new GatewayHandlerImpl();

		public CallableHandler(TChannel topChannel, Request request) {
			this.topChannel = topChannel;
			this.request = request;
		}

		@Override
		public Response call() throws Exception {
			if (topChannel.getTracer() == null) {
				return callWithoutTracing();
			}
			Span span = Tracing.startInboundSpan(request, topChannel.getTracer(), topChannel.getTracingContext());
			try {
				return callWithoutTracing();
			} catch (Exception e) {
				span.log("exception", e);
				throw e;
			} finally {
				span.finish();
				topChannel.getTracingContext().clear();
			}
		}

		private Response callWithoutTracing() throws Exception {

			Response response = handler.handle(request);
			request.release();
			return response;
		}
	}
}
