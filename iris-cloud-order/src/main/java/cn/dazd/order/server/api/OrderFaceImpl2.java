package cn.dazd.order.server.api;

import java.util.logging.Logger;

import org.apache.thrift.TException;

import cn.dazd.iris.core.annotation.ApiAnnotation;
import cn.dazd.iris.core.tchannel.thrift.Protocol;
import cn.dazd.iris.core.tchannel.thrift.ProviderService;
import cn.dazd.iris.core.tchannel.thrift.ProviderService.Iface;
import cn.dazd.iris.core.tchannel.thrift.Result;

/**
 * 订单模块API
 * 
 * @author Administrator
 *
 */
@ApiAnnotation(service = ProviderService.class)
public class OrderFaceImpl2 implements Iface {
	final Logger l = Logger.getLogger("OrderFaceImpl2");

	public Result proxy(Protocol body) throws TException {
		// TODO Auto-generated method stub
		return new Result(1000, "了解了解了解了解3了", "可理解拉开距离就");
	}

	public Result gateway(Protocol body, Protocol GBody2) throws TException {
		// TODO Auto-generated method stub
		return new Result(100, "了解了解3了", "可理解离就");
	}

}

// String uu = "hello world!";
// ByteBuffer bbf = ByteBuffer.wrap(uu.getBytes());
// 得到远程接口实例
// OrderSearchSPI orderSearchSPI = (OrderSearchSPI) SpiInstantiationKits
// .getRemoteInstanceSPI(OrderSearchSPI.class.getName());
// ByteBuffer bbf2 =
// orderSearchSPI.searchBySample(ByteBuffer.wrap("呵呵，跑起来看看.".getBytes()));
// String xx = new String(bbf2.array());
// l.info(xx);