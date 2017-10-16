package cn.dazd.iris.core.tchannel.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import org.apache.thrift.TBase;

import com.uber.tchannel.api.SubChannel;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.Request;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import io.netty.buffer.ByteBuf;

// 该类是单例无状态的
@SuppressWarnings("rawtypes")
public final class GatewayHandlerImpl extends ThriftRequestHandler<org.apache.thrift.TBase, TBase> {

	final Logger l = Logger.getLogger("GatewayHandlerImpl");

	// 代理把接收到请求及数据，在这里做转发
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public ThriftResponse<TBase> handleImpl(ThriftRequest<org.apache.thrift.TBase> request) {
		// 虚拟service路径，格式如下：/领域名称/服务名称
		String servicePath = "";
		// 虚拟endpoint路径，支持正则表达,格式如下：get.*,表示仅可访问get开头的api接口
		String endpointPath = "";
		String requestServicePath = request.getService();
		String requestEndpoint = request.getEndpoint();

		l.info(String.format("service:{%s},endpoint:{%s}", request.getService(), request.getEndpoint()));

		TChannel tchannelClient = new TChannel.Builder("iris-cloud-client").build();
		// 这里需要增加过滤器，实现中。。。。。，ip写死，测试用，这里是remote的IP和端口
		SubChannel subChannelClient = tchannelClient.makeSubChannel(request.getService())
				.setPeers(new ArrayList<InetSocketAddress>() {
					{
						// 从缓存里拿到
						add(new InetSocketAddress("192.168.199.181", 30120));
					}
				});

		ByteBuf arg3 = request.getArg3().copy();
		ByteBuf arg2 = request.getArg2().copy();
		ByteBuf arg1 = request.getArg1().copy();
		ThriftResponse<TBase> resultClient = null;
		ThriftRequest<TBase> req2 = null;
		try {
			req2 = (ThriftRequest<TBase>) Request.build(request.getArgScheme(),
					Calendar.getInstance().getTimeInMillis(), Calendar.getInstance().getTimeInMillis(),
					request.getService(), request.getTransportHeaders(), arg1, arg2, arg3);
			TFuture<ThriftResponse<TBase>> future = subChannelClient.send(req2);
			resultClient = future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new ThriftResponse.Builder<TBase>(request).setArg2(resultClient.getArg2())
				.setArg3(resultClient.getArg3()).build();
	}

	Class<?> getType(Class<?> cls) {
		return null;
	}
}
