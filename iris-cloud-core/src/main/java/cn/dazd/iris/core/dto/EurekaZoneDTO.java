package cn.dazd.iris.core.dto;

import java.io.Serializable;

/**
 * 注册中心配置
 * 
 * @author Administrator
 *
 */
public class EurekaZoneDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8788761166482763831L;

	private String ip = null;
	private String defaultZone = null;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDefaultZone() {
		return defaultZone;
	}

	public void setDefaultZone(String defaultZone) {
		this.defaultZone = defaultZone;
	}
}
