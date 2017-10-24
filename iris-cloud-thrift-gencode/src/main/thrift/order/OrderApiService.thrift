namespace java cn.dazd.iris.core.tchannel.thrift
include "../base/Protocol.thrift"
include "../base/Result.thrift"
const string VERSION = "0.2.0" 
service OrderApi{
    Result.Result updateOrder(1:Protocol.Protocol body)
}