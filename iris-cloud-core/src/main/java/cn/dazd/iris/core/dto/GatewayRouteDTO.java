package cn.dazd.iris.core.dto;

public class GatewayRouteDTO {

	/**
	 * 路由id,表明来自那个网关
	 */
	String id = null;

	/**
	 * endpoint规则配置, 例如：get*.
	 */
	String path = null;

	/**
	 * 路由映射的服务进程名称
	 */
	String serviceId = null;

	public GatewayRouteDTO(String id, String path, String serviceId) {
		this.id = id;
		this.path = path;
		this.serviceId = serviceId;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
