package cn.dazd.iris.core.router;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.thrift.TBase;

import com.uber.tchannel.api.SubChannel;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.api.errors.TChannelError;
import com.uber.tchannel.messages.Request;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import io.netty.buffer.ByteBuf;

public class RpcKits {
	final static Logger l = Logger.getLogger("RpcKits");
	public static TChannel TCHANNEL_CLIENT = new TChannel.Builder("iris-gateway-client").build();

	/**
	 * 转发请求
	 * 
	 * @param request
	 *            request对象
	 * @param List<InetSocketAddress>
	 *            peers 接收数据的地址
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static ThriftResponse<TBase<?, ?>> sendRequest(ThriftRequest<TBase<?, ?>> request,
			List<InetSocketAddress> peers) {
		SubChannel subChannelClient = TCHANNEL_CLIENT.makeSubChannel(request.getService()).setPeers(peers);
		ByteBuf arg3 = request.getArg3().retain();
		ByteBuf arg2 = request.getArg2().retain();
		ByteBuf arg1 = request.getArg1().retain();
		ThriftResponse<TBase<?, ?>> resultClient = null;
		ThriftRequest<TBase<?, ?>> req2 = null;
		try {
			req2 = (ThriftRequest<TBase<?, ?>>) Request.build(request.getArgScheme(),
					Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis(),
					request.getService(), request.getTransportHeaders(), arg1, arg2, arg3);
			TFuture<ThriftResponse<TBase<?, ?>>> future = subChannelClient.send(req2);
			resultClient = future.get();
			return new ThriftResponse.Builder<TBase<?, ?>>(request).setArg2(resultClient.getArg2())
					.setArg3(resultClient.getArg3()).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <args extends TBase<?, ?>, result extends TBase<?, ?>> ThriftResponse<result> sendRequest(
			ThriftRequest<args> request, ArrayList<InetSocketAddress> peers)
			throws TChannelError, InterruptedException, ExecutionException {
		SubChannel subChannelClient = TCHANNEL_CLIENT.makeSubChannel(request.getService()).setPeers(peers);
		ByteBuf arg3 = request.getArg3().retain();
		ByteBuf arg2 = request.getArg2().retain();
		ByteBuf arg1 = request.getArg1().retain();
		ThriftRequest<args> req2 = null;
		req2 = (ThriftRequest<args>) Request.build(request.getArgScheme(), Calendar.getInstance().getTimeInMillis(),
				Calendar.getInstance().getTimeInMillis(), request.getService(), request.getTransportHeaders(), arg1,
				arg2, arg3);
		TFuture<ThriftResponse<result>> future = subChannelClient.send(req2);
		ThriftResponse<result> resultClient = future.get();
		if (resultClient.isError()) {
			String errMsg = new StringBuffer(resultClient.getError().getErrorType().name()).append("::")
					.append(resultClient.getError().getMessage()).append("::").append(peers.toString()).toString();
			l.log(Level.SEVERE, errMsg);
			return null;
		}
		return new ThriftResponse.Builder<result>(request).setArg2(resultClient.getArg2())
				.setArg3(resultClient.getArg3()).build();

	}
}
