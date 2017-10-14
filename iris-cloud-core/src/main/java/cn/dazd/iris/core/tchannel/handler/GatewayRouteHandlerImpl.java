package cn.dazd.iris.core.tchannel.handler;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.logging.Logger;

import com.google.common.util.concurrent.ListenableFuture;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.tchannel.thrift.ProviderService;
import cn.dazd.iris.core.tchannel.thrift.ProviderService.gateway_args;
import cn.dazd.iris.core.tchannel.thrift.ProviderService.gateway_result;

// 这个也要优化
public class GatewayRouteHandlerImpl
		extends ThriftRequestHandler<ProviderService.gateway_args, ProviderService.gateway_result> {

	private String endpoint;
	private String serviceName;
	private String serviceId;
	final Logger l = Logger.getLogger("GatewayRouteHandler");
	private static TChannel tchannel = null;

	@Override
	public ThriftResponse<gateway_result> handleImpl(ThriftRequest<gateway_args> request) {
		// TODO Auto-generated method stub
		l.info("路由开始工作!");
//		long stime = Calendar.getInstance().getTimeInMillis();
//		ByteBuffer args = request.getBody(gateway_args.class).bufferForBody();
//		l.info("===>>>" + new String(args.array()));
//		if (tchannel == null) {
//			tchannel = new TChannel.Builder("iris-gateway-client").build();
//			tchannel.makeSubChannel("iris-gateway-client");
//		}
//		ByteBuffer result = null;
//		try {
//			ListenableFuture<ThriftResponse<gateway_result>> future = tchannel
//					.getSubChannel("iris-gateway-client").send(
//							new ThriftRequest.Builder<gateway_args>(serviceName, endpoint)
//									.setBody(new gateway_args(args)).setTimeout(3000).build(),
//							InetAddress.getLocalHost(), 30100);
//			ThriftResponse<gateway_result> getResult = future.get();
//			result = getResult.getBody(gateway_result.class).bufferForSuccess();
//			getResult.release();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// tchannel.shutdown(true);
//		}
//		long etime = Calendar.getInstance().getTimeInMillis();
//		l.info("耗时：" + (etime - stime));
//		return new ThriftResponse.Builder<ProviderService.gateway_result>(request).setBody(new gateway_result(result))
//				.build();
		return null;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
