package cn.dazd.iris.core.enums;

/**
 * 访问接口方法的类型枚举
 * 
 * @author Administrator
 *
 */
public enum MethodTypeEnum {
	GATEWAY_NAME("GatewayAnnotation"), PROXY_NAME("ProxyAnnotation");

	private final String prefix = "";
	private final String type;

	MethodTypeEnum(String type) {
		this.type = type;
	}

	public String getValue() {
		return prefix + this.type;
	}
}
