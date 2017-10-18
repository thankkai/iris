namespace java cn.dazd.iris.core.tchannel.thrift
const string VERSION = "0.2.0"
struct Result{
 1: required i32 code;
 2: required string message;
 3: required string data;
}