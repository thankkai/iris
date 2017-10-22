/**
 * 
 */
package cn.dazd.iris.core.tchannel.handler;

import java.util.logging.Logger;

import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.tchannel.thrift.HostInfo;
import cn.dazd.iris.core.tchannel.thrift.Result;
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService;

/**
 * @author Administrator
 *
 */
public class ToEurekaHandlerImpl
		extends ThriftRequestHandler<EurekaService.toEureka_args, EurekaService.toEureka_result> {

	final Logger l = Logger.getLogger("ToEurekaHandlerImpl");

	@Override
	public ThriftResponse<EurekaService.toEureka_result> handleImpl(
			ThriftRequest<EurekaService.toEureka_args> request) {

		HostInfo hostInfo = request.getBody(EurekaService.toEureka_args.class).getHostInfo();
		l.info("=============>" + hostInfo.getIp());
		EurekaService.toEureka_result re = new EurekaService.toEureka_result();
		re.setSuccess(new Result() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				this.setCode(200);
				this.setData("ok");
				this.setMessage("success");
			}
		});
		return new ThriftResponse.Builder<EurekaService.toEureka_result>(request).setBody(re).build();
	}

}
