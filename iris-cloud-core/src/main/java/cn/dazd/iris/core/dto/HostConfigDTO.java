package cn.dazd.iris.core.dto;

import java.util.ArrayList;
import java.util.List;

import cn.dazd.iris.core.kit.HostConfigKits;

/**
 * 当前运行实例配置
 * 
 * @author Administrator
 *
 */
public class HostConfigDTO {

	private String ip;
	/**
	 * 端口
	 */
	private Integer port;

	/**
	 * 实例进程号
	 */
	private Integer processId;

	/**
	 * 服务通道命名
	 */
	private String serviceName;

	/**
	 * 终结点
	 */
	private String endpoint;

	/**
	 * 实例描述
	 */
	private String description;

	/**
	 * 密钥
	 */
	private String hostSecret;

	/**
	 * 实例所在位置简称：hz、nj、bj
	 */
	private String dcName;

	private List<EurekaZoneDTO> ezlist = new ArrayList<>();

	public List<EurekaZoneDTO> getEzlist() {
		return ezlist;
	}

	public void setEzlist(List<EurekaZoneDTO> ezlist) {
		this.ezlist = ezlist;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHostCode() {
		return HostConfigKits.getHostCode(ip, port, dcName);
	}

	// public void setHostCode(String hostCode) {
	// this.hostCode = hostCode;
	// }

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getHostSecret() {
		return hostSecret;
	}

	public void setHostSecret(String hostSecret) {
		this.hostSecret = hostSecret;
	}
}
