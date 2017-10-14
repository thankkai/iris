package cn.dazd.order.client;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import cn.dazd.iris.core.tchannel.thrift.Protocol;
import cn.dazd.iris.core.tchannel.thrift.Result;
import io.netty.buffer.ByteBufInputStream;

public class OrderTest {
	final Logger l = Logger.getLogger("GatewayClientTest");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws InterruptedException, ExecutionException {

		TChannel tchannel = null;
		try {
			tchannel = new TChannel.Builder("iris-cloud-client").build();
			// gateway_args args = new
			// gateway_args(ByteBuffer.wrap("我给点数据试试！".getBytes()));
			Protocol ppp = new Protocol();

			// Protocol2 args = new Protocol2(ppp, ppp);

			ListenableFuture<ThriftResponse<Result>> future = tchannel.makeSubChannel("iris-cloud-client").send(
					new ThriftRequest.Builder<Protocol>("orderapi", "updateorder").setBody(ppp).build(),
					InetAddress.getLocalHost(), 30100);
			ThriftResponse<Result> getResult = future.get();
			ByteBufInputStream bbf = new ByteBufInputStream(getResult.getArg3());
			TTransport tt = new TIOStreamTransport(bbf);
			TProtocol tp = new TBinaryProtocol(tt);
			Result bbfx = new Result();
			bbfx.read(tp);
			l.info(new String(bbfx.getMessage()));
			// System.out.println(value.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tchannel.shutdown();
		}

	}

}
