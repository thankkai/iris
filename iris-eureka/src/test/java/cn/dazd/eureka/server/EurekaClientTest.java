package cn.dazd.eureka.server;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.api.TFuture;
import com.uber.tchannel.api.errors.TChannelError;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.tchannel.thrift.HostInfo;
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService;
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService.toEureka_args;

public class EurekaClientTest {

	@Test
	public void test() {

		TChannel tchannel = new TChannel.Builder("iris-cloud-eureka-client").build();

		List<InetSocketAddress> routers = new ArrayList<InetSocketAddress>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(new InetSocketAddress("127.0.0.1", 30001));
			}
		};

		ThriftRequest<toEureka_args> request = new ThriftRequest.Builder<EurekaService.toEureka_args>("EurekaService",
				"EurekaService::toEureka").setBody(new EurekaService.toEureka_args() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
						this.setHostInfo(new HostInfo() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								this.setIp("0.0.0.0");
								this.setPort(30120);
								this.setServiceName("iris-cloud-gateway");
								this.setProcessId(1);
							}
						});
					}
				}).setTimeout(3000).build();
		try {
			TFuture<ThriftResponse<EurekaService.toEureka_result>> f = tchannel
					.makeSubChannel("iris-cloud-eureka-client").setPeers(routers).send(request);
			ThriftResponse<EurekaService.toEureka_result> re = f.get();
			
			System.out.println(re.getBody(EurekaService.toEureka_result.class).getSuccess().getMessage());
			assertEquals(re.isError(), false);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TChannelError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
