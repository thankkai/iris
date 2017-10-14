package cn.dazd.iris.core.tchannel.handler;

import com.uber.tchannel.messages.ThriftRequest;

public interface RpcInvoke<T> {

	String invoke(ThriftRequest<T> t);
}
