package cn.dazd.iris.core.tchannel.handler;

import com.uber.tchannel.messages.ThriftRequest;

import cn.dazd.iris.core.tchannel.thrift.ProviderService;



public class ProxyRpcInvokeImpl implements RpcInvoke<ProviderService.proxy_args> {

	@Override
	public String invoke(ThriftRequest<ProviderService.proxy_args> t) {
		// TODO Auto-generated method stub
		return "the proxy rpc is successed.";
	}

}
