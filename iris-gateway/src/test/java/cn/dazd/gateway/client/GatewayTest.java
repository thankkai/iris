package cn.dazd.gateway.client;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.util.concurrent.ListenableFuture;
import com.uber.tchannel.api.SubChannel;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.tchannel.thrift.ProviderService.gateway_args;
import cn.dazd.iris.core.tchannel.thrift.ProviderService.gateway_result;

public class GatewayTest {
	final static Logger l = Logger.getLogger("GatewayTest");
	final static TChannel tchannel = new TChannel.Builder("iris-gateway-client-test").build();

	public static void main(String[] args) {

		// int yz = 10;
		// ExecutorService fixedThreadPool = Executors.newFixedThreadPool(yz);
		// final Semaphore semaphore = new Semaphore(yz);
		// final SubChannel subch =
		// tchannel.makeSubChannel("iris-gateway-client-test");
		//
		// long stime = Calendar.getInstance().getTimeInMillis();
		//
		// gateway_args argsx = new
		// gateway_args(ByteBuffer.wrap("给我路由到订单！".getBytes()));
		// try {
		// ListenableFuture<ThriftResponse<gateway_result>> future = subch
		// .send(new ThriftRequest.Builder<gateway_args>("orderapi",
		// "createorder").setBody(argsx)
		// .setTimeout(5000).build(), InetAddress.getLocalHost(), 30050);
		// ThriftResponse<gateway_result> getResult = future.get();
		// ByteBuffer bbf =
		// getResult.getBody(gateway_result.class).bufferForSuccess();
		// getResult.release();
		// l.info(new String(bbf.array()));
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		// long etime = Calendar.getInstance().getTimeInMillis();
		// l.info("耗时：" + (etime - stime));
		// for (int i = 0; i < yz; i++) {
		// fixedThreadPool.execute(new Runnable() {
		//
		// public void run() {
		// try {
		// semaphore.acquire();
		// Thread.sleep(RandomUtils.nextInt(100, 200));
		// long stime = Calendar.getInstance().getTimeInMillis();
		// // System.out.println(h + "x");
		//
		// gateway_args args = new
		// gateway_args(ByteBuffer.wrap("给我路由到订单！".getBytes()));
		// try {
		// ListenableFuture<ThriftResponse<gateway_result>> future = subch
		// .send(new ThriftRequest.Builder<gateway_args>("orderapi",
		// "createorder")
		// .setBody(args).setTimeout(5000).build(), InetAddress.getLocalHost(),
		// 30050);
		// ThriftResponse<gateway_result> getResult = future.get();
		// ByteBuffer bbf =
		// getResult.getBody(gateway_result.class).bufferForSuccess();
		// getResult.release();
		// l.info(new String(bbf.array()));
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		// long etime = Calendar.getInstance().getTimeInMillis();
		//
		// l.info("耗时：" + (etime - stime));
		// } catch (Exception e) {
		// l.info(e.getMessage());
		// } finally {
		// // tchannel.shutdown();
		// semaphore.release();
		// }
		// }
		// });
		// }
		//
		// tchannel.shutdown(false);
	}

}
