namespace java cn.dazd.iris.core.tchannel.thrift.eureka
include "../base/HostInfo.thrift"
include "../base/Result.thrift"
const string VERSION = "0.2.0" 
service EurekaService{
    Result.Result toEureka(1:HostInfo.HostInfo hostInfo)
}