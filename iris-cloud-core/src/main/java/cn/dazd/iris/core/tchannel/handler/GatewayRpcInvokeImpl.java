package cn.dazd.iris.core.tchannel.handler;

import com.uber.tchannel.messages.ThriftRequest;

import cn.dazd.iris.core.tchannel.thrift.ProviderService;


public class GatewayRpcInvokeImpl implements RpcInvoke<ProviderService.gateway_args> {

	@Override
	public String invoke(ThriftRequest<ProviderService.gateway_args> t) {
		// TODO Auto-generated method stub
		return "the gateway rpc is successed.";
	}

}
