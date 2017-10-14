package cn.dazd.iris.core.enums;

/**
 * 终端结束点枚举
 * 
 * @author Administrator
 *
 */
public enum EndpointEnum {
	GATEWAY_NAME("ProviderService::gateway"), PROXY_NAME("ProviderService::proxy");

	private final String prefix = "";
	private final String type;

	EndpointEnum(String type) {
		this.type = type;
	}

	public String getValue() {
		return prefix + this.type;
	}
}
