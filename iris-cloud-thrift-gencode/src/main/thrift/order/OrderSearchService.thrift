namespace java cn.dazd.iris.core.tchannel.thrift
include "../base/Protocol.thrift"
include "../base/Result.thrift"
const string VERSION = "0.2.0" 
service ProviderService{
    Result.Result gateway(1:Protocol.Protocol body,2:Protocol.Protocol GBody2)
    Result.Result proxy(1:Protocol.Protocol body)
}