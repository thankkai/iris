namespace java cn.dazd.iris.core.tchannel.thrift
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

struct Result{
 1: required i32 code;
 2: required string message;
 3: required string data;
}

service ProviderService{
    Result gateway(1:Protocol body,2:Protocol GBody2)
    Result proxy(1:Protocol body)
}

service OrderApi{
    Result updateOrder(1:Protocol body)
}