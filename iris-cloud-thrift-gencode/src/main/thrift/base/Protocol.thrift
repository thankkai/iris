namespace java cn.dazd.iris.core.tchannel.thrift
const string VERSION = "0.2.0" 
struct Protocol {
 1: required string targetMethodVersion;
 2: required string targetMethodName;
 3: required string targetDcName;
 4: required string sourceSign;
 5: required string sourceHostCode;
 6: required string token;
 7: optional i64 tradeTimestamp;
 8: optional string data;
 9: optional binary file;
}