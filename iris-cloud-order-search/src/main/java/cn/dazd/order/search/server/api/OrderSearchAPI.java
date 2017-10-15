package cn.dazd.order.search.server.api;

import org.apache.thrift.TException;

import cn.dazd.iris.core.annotation.ApiAnnotation;
import cn.dazd.iris.core.tchannel.thrift.Protocol;
import cn.dazd.iris.core.tchannel.thrift.ProviderService;
import cn.dazd.iris.core.tchannel.thrift.ProviderService.Iface;
import cn.dazd.iris.core.tchannel.thrift.Result;

@ApiAnnotation(service = ProviderService.class)
public class OrderSearchAPI implements Iface {

	public Result gateway(Protocol body, Protocol GBody2) throws TException {
		// TODO Auto-generated method stub
		return new Result(50, "是打发打发", "翻噶地方是");
	}

	public Result proxy(Protocol body) throws TException {
		// TODO Auto-generated method stub
		return new Result(99, "了解了解了解了解了", "可理解拉开距离就");
	}
}
