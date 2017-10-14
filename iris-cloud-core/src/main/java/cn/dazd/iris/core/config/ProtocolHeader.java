package cn.dazd.iris.core.config;

/**
 * 协议头部约定类
 * 
 * @author Administrator
 *
 */
public final class ProtocolHeader {

	/**
	 * 被调用方的方法类型（GET/UPDATE/SAVE/DELETE）
	 */
	private final static String methodTypeField = "methodType";

	/**
	 * 注解名和方法名的拼接符
	 */
	private final static String methodTypeFlag = "@";

	/**
	 * 被调用方的方法版本，必须
	 */
	private final static String methodVerField = "methodVersion";

	/**
	 * 被调用方的方法名
	 */
	private final static String methodNameField = "methodName";

	/**
	 * 被调用方的别名，必须
	 */
	private final static String methodAliasField = "methodAlias";

	/**
	 * 方法的Json格式参数，可选
	 */
	private final static String methodJsonParamField = "methodJsonParam";

	/**
	 * 方法的二进制参数，可选
	 */
	private final static String methodByteParamField = "methodByteParam";

	/**
	 * 被调用方的部署地点，必选
	 */
	private final static String targetDcName = "targetDcName";

	/**
	 * 请求方的票据，必须
	 */
	private final static String tokenField = "token";

	/**
	 * 请求方的签名，必须
	 */
	private final static String signField = "sign";

	/**
	 * 请求方每次请求的时间戳
	 */
	private final static String tradeTimestampField = "tradeTimestamp";

	/**
	 * 源IP参数
	 */
	private final static String sourceIpField = "sourceIp";

	/**
	 * 源端口参数
	 */
	private final static String sourcePortField = "sourcePort";
	/**
	 * 源实例编号
	 */
	private final static String sourceHostCodeField = "sourceHostCode";
	/**
	 * 源实例进程号
	 */
	private final static String sourceProcessIdField = "sourceProcessId";

	/**
	 * 源实例所属区域
	 */
	private final static String sourceDcNameField = "sourceDcName";

	public static String getMethodVerField() {
		return methodVerField;
	}

	public static String getMethodTypeFlag() {
		return methodTypeFlag;
	}

	public static String getMethodTypeField() {
		return methodTypeField;
	}

	public static String getMethodNameField() {
		return methodNameField;
	}

	public static String getSignfield() {
		return signField;
	}

	public static String getSourceIpField() {
		return sourceIpField;
	}

	public static String getSourcePortField() {
		return sourcePortField;
	}

	public static String getSourceHostcodeField() {
		return sourceHostCodeField;
	}

	public static String getSourceProcessidField() {
		return sourceProcessIdField;
	}

	public static String getTokenField() {
		return tokenField;
	}

	public static String getMethodJsonParamField() {
		return methodJsonParamField;
	}

	public static String getMethodByteParamField() {
		return methodByteParamField;
	}

	public static String getSourceDcNameField() {
		return sourceDcNameField;
	}

	public static String getTargetDcName() {
		return targetDcName;
	}

	public static String getMethodAliasField() {
		return methodAliasField;
	}

	public static String getTradeTimestampField() {
		return tradeTimestampField;
	}

}
