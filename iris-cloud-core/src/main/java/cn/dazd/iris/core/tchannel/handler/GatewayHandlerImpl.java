package cn.dazd.iris.core.tchannel.handler;

import java.util.logging.Logger;

import org.apache.thrift.TBase;

import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

// 该类是单例无状态的，不允许定义任何线程不安全的变量
public final class GatewayHandlerImpl extends ThriftRequestHandler<TBase<?, ?>, TBase<?, ?>> {

	final Logger l = Logger.getLogger("GatewayHandlerImpl");
	private String service = null;
	private String endpoint = null;

	// 代理把接收到请求及数据，在这里做转发
	@Override
	public ThriftResponse<TBase<?, ?>> handleImpl(ThriftRequest<TBase<?, ?>> request) {
		l.info(String.format("service:{%s},endpoint:{%s}", service, endpoint));
		return new ThriftResponse.Builder<TBase<?, ?>>(request).setTransportHeaders(request.getTransportHeaders())
				.setBody(null).build();
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
