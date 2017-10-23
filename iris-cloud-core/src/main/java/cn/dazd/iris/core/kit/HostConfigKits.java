package cn.dazd.iris.core.kit;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.dazd.iris.core.config.ProtocolHeader;
import cn.dazd.iris.core.dto.HostConfigDTO;

/**
 * 主机实例工具
 *
 * @author Administrator
 *
 */
public class HostConfigKits {
	private static Logger logger = LoggerFactory.getLogger(HostConfigKits.class);
	private static HostConfigDTO hostConfigDTO = null;
	public static final String PROTOTOL_PROPERTY_FILE = "protocol.properties";
	public static final String APPLICATION_APPCONFIG_FILE = "appconfig.yml";

	/**
	 * 获取主机配置实例
	 * 
	 * @return
	 */
	@Deprecated
	public static HostConfigDTO getHostConfig() {
		if (hostConfigDTO == null) {
			try {
				Properties prop = PropertiesUtil.loadProperties(PROTOTOL_PROPERTY_FILE);
				hostConfigDTO = new HostConfigDTO();
				hostConfigDTO.setProcessId(new Integer(prop.getProperty("host.processId")));
				hostConfigDTO.setIp(prop.getProperty("host.ip"));
				hostConfigDTO.setPort(new Integer(prop.getProperty("host.port")));
				hostConfigDTO.setServiceName(prop.getProperty("host.serviceName"));
			} catch (Exception e) {
				logger.error("加载host配置文件失败，请检查。文件名：" + PROTOTOL_PROPERTY_FILE);
				logger.error("异常信息：" + e.getMessage());
			}
		}
		return hostConfigDTO;
	}

	/**
	 * 获取主机配置实例
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HostConfigDTO getAppConfig() {
		if (hostConfigDTO == null) {
			try {
				String yamlStr = PropertiesUtil.loadYaml(APPLICATION_APPCONFIG_FILE);
				Gson gson = new Gson();
				JsonObject jobj = gson.fromJson(yamlStr, JsonObject.class);
				JsonObject host = jobj.get("host").getAsJsonObject();
				String ip = host.get("ip").getAsString();
				int port = host.get("port").getAsInt();
				String serviceName = host.get("serviceName").getAsString();
				int processId = host.get("processId").getAsInt();

				hostConfigDTO = new HostConfigDTO();
				hostConfigDTO.setProcessId(processId);
				hostConfigDTO.setIp(ip);
				hostConfigDTO.setPort(port);
				hostConfigDTO.setServiceName(serviceName);

				JsonElement defaultZone = host.get("defaultZone");
				if (null != defaultZone) {
					hostConfigDTO.setZoneList(
							gson.fromJson(host.get("defaultZone"), hostConfigDTO.getZoneList().getClass()));
				}
			} catch (Exception e) {
				logger.error("加载host配置文件失败，" + e.getMessage());
			}
		}
		return hostConfigDTO;
	}

	/**
	 * 获取签名
	 * 
	 * @param hc
	 * @return
	 */
	public static String getSign(HostConfigDTO hc, Long tradeTimestamp) {
		return getSign(hc.getIp(), hc.getPort(), hc.getDcName(), hc.getHostSecret(), tradeTimestamp);
	}

	/**
	 * 获取签名 dcname+ip+port+hosecode+毫秒数时间戳
	 * 
	 * @param ip
	 *            IP域名
	 * @param port
	 *            端口
	 * @param dcName
	 *            区域
	 * @param hostSecret
	 *            秘钥
	 * @param tradeTimestamp
	 *            请求时间
	 * @return
	 */
	public static String getSign(String ip, Integer port, String dcName, String hostSecret, Long tradeTimestamp) {
		HashMap<String, String> stringStringHashMap = new HashMap<>();
		stringStringHashMap.put(ProtocolHeader.getSourceDcNameField(), dcName);
		stringStringHashMap.put(ProtocolHeader.getSourceIpField(), ip);
		stringStringHashMap.put(ProtocolHeader.getSourcePortField(), String.valueOf(port));
		stringStringHashMap.put(ProtocolHeader.getTradeTimestampField(), String.valueOf(tradeTimestamp));
		stringStringHashMap.put(ProtocolHeader.getSourceHostcodeField(), getHostCode(ip, port, dcName));
		return signTopRequest(stringStringHashMap, hostSecret);
	}

	/**
	 * 生成hostCode
	 * 
	 * @param ip
	 * @param port
	 * @param dcName
	 * @return
	 */
	public static String getHostCode(String ip, Integer port, String dcName) {
		StringBuffer sb = new StringBuffer().append(ip).append(port).append(dcName);
		return DigestUtils.md5Hex(sb.toString()).toUpperCase();
	}

	/**
	 * 签名加密，参考阿里的md5方式加密http://open.taobao.com/docs/doc.htm?spm=a219a.7629140.0.0.iDTpQ9&treeId=49&articleId=101617&docType=1&qq-pf-to=pcqq.c2c
	 * 
	 * @param params
	 * @param secret
	 * @return
	 */
	public static String signTopRequest(Map<String, String> params, String secret) {
		// 第一步：检查参数是否已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		// 第二步：把所有参数名和参数值串在一起,secret放两头
		StringBuilder query = new StringBuilder().append(secret);
		for (String key : keys) {
			String value = params.get(key);
			if (!Validate.isNullOrEmpty(key) && Validate.isNullOrEmpty(value)) {
				query.append(key).append(value);
			}
		}
		query.append(secret);
		// 第三步：使用MD5加密
		return DigestUtils.md5Hex(query.toString()).toUpperCase();
	}

}
