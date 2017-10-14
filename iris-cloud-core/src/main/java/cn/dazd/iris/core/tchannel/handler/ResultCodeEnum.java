package cn.dazd.iris.core.tchannel.handler;

/**
 * Created by chenyu on 2017/7/17.
 */
public enum ResultCodeEnum {
    SUCCESS_0(0, "success"),
    SERVER_ERROR_500(500, "server error"),
    METHOD_VERSION_DIFFERENT_409(409, "failed due to the method version is different."),
    NOFOUND_404(404, "method not found."),
    UNAUTHORIZED_401(401, "unauthorized" ),
    SIGN_WRONG_400(400, "wrong sign"),
    RESULT_WRONG(401, "result type wrong or result is null" ),
    TIME_OUT(504, "TradeTimestamp time out" );

    private final int type;

    private final String message;
    ResultCodeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return this.type;
    }

    public static Result getResultByResultCodeEnum(ResultCodeEnum resultCodeEnum){
        Result result = new Result();
        result.setCode(resultCodeEnum.getValue());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }
}

