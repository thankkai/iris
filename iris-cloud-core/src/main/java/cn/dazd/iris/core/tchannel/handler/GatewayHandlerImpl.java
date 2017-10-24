package cn.dazd.iris.core.tchannel.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.thrift.TBase;

import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.router.RpcKits;

// 该类是单例无状态的
@SuppressWarnings("rawtypes")
public final class GatewayHandlerImpl extends ThriftRequestHandler<TBase<?, ?>, TBase<?, ?>> {

	final Logger l = Logger.getLogger("GatewayHandlerImpl");

	// 代理把接收到请求及数据，在这里做转发
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public ThriftResponse<TBase<?, ?>> handleImpl(ThriftRequest<TBase<?, ?>> request) {
		// 虚拟service路径，格式如下：/领域名称/服务名称
		String servicePath = "";
		// 虚拟endpoint路径，支持正则表达,格式如下：get.*,表示仅可访问get开头的api接口
		String endpointPath = "";
		String requestServicePath = request.getService();
		String requestEndpoint = request.getEndpoint();

		l.info(String.format("service:{%s},endpoint:{%s}", request.getService(), request.getEndpoint()));

		// 这里需要增加过滤器，实现中。。。。。，ip写死，测试用，这里是remote的IP和端口
		List peers = new ArrayList<InetSocketAddress>() {
			{
				// 从缓存里拿到
				add(new InetSocketAddress("127.0.0.1", 30120));
			}
		};
		return RpcKits.sendRequest(request, peers);
	}

}
