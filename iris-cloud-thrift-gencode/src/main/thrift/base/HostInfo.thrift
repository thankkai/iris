namespace java cn.dazd.iris.core.tchannel.thrift
const string VERSION = "0.2.0" 
struct HostInfo {
 1: required string ip;
 2: required i32 port;
 3: required string serviceName;
 4: required i32 processId;
}