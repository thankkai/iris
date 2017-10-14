package cn.dazd.iris.core.config;

public class GatewayRoute {

	/**
	 * 路由id,表明来自那个网关
	 */
	String id = null;

	/**
	 * 方法所对应的路由路径正则, 例如：get*.
	 */
	String methodName = null;

	/**
	 * 路由映射的服务进程名称
	 */
	String serviceId = null;

	public GatewayRoute(String id, String methodName, String serviceId) {
		this.id = id;
		this.methodName = methodName;
		this.serviceId = serviceId;

	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
