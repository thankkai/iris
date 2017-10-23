package cn.dazd.iris.core.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.thrift.TBase;
import org.springframework.core.annotation.AnnotationUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uber.tchannel.api.handlers.RequestHandler;
import com.uber.tchannel.messages.ThriftRequest;

import cn.dazd.iris.core.annotation.EnableEurekaServer;
import cn.dazd.iris.core.annotation.EnableGatewayProxy;
import cn.dazd.iris.core.annotation.EnableToEureka;
import cn.dazd.iris.core.dto.EurekaZoneDTO;
import cn.dazd.iris.core.dto.HostConfigDTO;
import cn.dazd.iris.core.exception.MethodScanConfigException;
import cn.dazd.iris.core.kit.ApiRegistryKits;
import cn.dazd.iris.core.kit.HostConfigKits;
import cn.dazd.iris.core.kit.PropertiesUtil;
import cn.dazd.iris.core.plugin.IPlugin;
import cn.dazd.iris.core.plugin.PluginCollector;
import cn.dazd.iris.core.router.RpcKits;
import cn.dazd.iris.core.tchannel.thrift.HostInfo;
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService.toEureka_args;

/**
 * 配置所需要的资源
 * 
 * @author Administrator
 *
 */
public final class ProtocolConfig {

	private final static Logger l = Logger.getLogger("ProtocolConfig");

	public final static PluginCollector IRIS_PLUGINS = new PluginCollector();

	public final static Map<String, RequestHandler> ENDPOINT_HANDLER = new Hashtable<>();

	static void initialize(AbstarctProtocolConfig config) {
		config.configPlugin(IRIS_PLUGINS);

		// 初始化本地需要注册的endpoint
		initEndpointPools();

		// 判断是否标记了注册动作
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableToEureka.class, config.getClass())) {
			ConfigWrap.ENABLE_TO_EUREKA = true;
			// 获取注册中心地址列表
			final HostConfigDTO hcDTO = HostConfigKits.getAppConfig();
			final toEureka_args args = new toEureka_args();
			args.setEndpoints(new ArrayList<String>() {
				private static final long serialVersionUID = -6714878897225577917L;
				{
					for (String key : ENDPOINT_HANDLER.keySet()) {
						this.add(key);
					}
				}
			});
			args.setHostInfo(new HostInfo() {
				private static final long serialVersionUID = 6200369699904167142L;
				{
					this.setIp(hcDTO.getIp());
					this.setPort(hcDTO.getPort());
					this.setProcessId(hcDTO.getProcessId());
					this.setServiceName(hcDTO.getServiceName());
				}
			});

			@SuppressWarnings("rawtypes")
			ThriftRequest<TBase> request = new ThriftRequest.Builder<TBase>(ProtocolBuilder.EUREKA_SERVICE,
					ProtocolBuilder.EUREKA_ENDPOINT).setBody(args).build();
			boolean isError = RpcKits.sendRequest(request, new ArrayList<InetSocketAddress>() {
				private static final long serialVersionUID = -2168116154462801927L;
				{
					for (EurekaZoneDTO obj : hcDTO.getEzlist()) {
						this.add(new InetSocketAddress(obj.getIp(), obj.getPort()));
					}
				}
			}).isError();
			if (!isError == true) {
				l.log(Level.SEVERE, "注册中心未连接成功，启动失败。");
				throw new RuntimeException("严重：注册中心未连接成功，启动失败。");
			}
		}

		// 判断是否标记了注册中心
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableEurekaServer.class, config.getClass())) {
			ConfigWrap.ENABLE_EUREKA_SERVER = true;
		}

		// 判断是否标记了网关代理
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableGatewayProxy.class, config.getClass())) {
			ConfigWrap.ENABLE_GATEWAY_PROXY = true;
		}

		if (ConfigWrap.ENABLE_EUREKA_SERVER == ConfigWrap.ENABLE_TO_EUREKA == true) {
			throw new RuntimeException("不允许注册中心与注册动作同时标记");
		}
	}

	/**
	 * 加入来自Object类的插件，多用于内部XML配置构造对象
	 * 
	 * @param obj
	 */
	static void addPluginFromObj(Object obj) {
		if (obj instanceof IPlugin) {
			IRIS_PLUGINS.add((IPlugin) obj);
		}
	}

	// 对注册中心进行连接，连接失败则启动失败
	static void toEureka() {

	}

	/**
	 * 收集本地需要注册的endpoint
	 */
	private static void initEndpointPools() {
		try {
			String yamlStr = PropertiesUtil.loadYaml(HostConfigKits.APPLICATION_APPCONFIG_FILE);
			Gson gson = new Gson();
			JsonObject jo = gson.fromJson(yamlStr, JsonObject.class);
			JsonArray array = jo.get("package").getAsJsonArray();
			if (null == array) {
				throw new MethodScanConfigException("the property for the package must not be null.");
			}
			List<String> property = new ArrayList<String>();
			for (JsonElement jsonElement : array) {
				property.add(jsonElement.getAsString());
			}
			ApiRegistryKits spm = new ApiRegistryKits(property);
			spm.initGlobalClassInstance();
		} catch (Exception e) {
			l.info(e.getMessage());
		}
	}

	/**
	 * 配置包装类，判断当前应用是否要注册、是否开启网关代理(后续可扩展)
	 * 
	 * @author Administrator
	 *
	 */
	public static class ConfigWrap {
		// 默认不开启注册中心
		public static boolean ENABLE_EUREKA_SERVER = false;

		// 默认不开启网关代理
		public static boolean ENABLE_GATEWAY_PROXY = false;

		// 默认不开启注册
		public static boolean ENABLE_TO_EUREKA = false;
	};

}
