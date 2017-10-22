package cn.dazd.iris.core.config;

import org.springframework.core.annotation.AnnotationUtils;

import cn.dazd.iris.core.annotation.EnableEurekaServer;
import cn.dazd.iris.core.annotation.EnableGatewayProxy;
import cn.dazd.iris.core.annotation.EnableToEureka;
import cn.dazd.iris.core.plugin.IPlugin;
import cn.dazd.iris.core.plugin.PluginCollector;

/**
 * 配置所需要的资源
 * 
 * @author Administrator
 *
 */
public final class ProtocolConfig {

	final static PluginCollector irisPlugins = new PluginCollector();

	static PluginCollector getIrisplugins() {
		return irisPlugins;
	}

	static void init(AbstarctProtocolConfig config) {
		config.configPlugin(irisPlugins);

		// 判断是否标记了注册中心
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableEurekaServer.class, config.getClass())) {
			ConfigWrap.ENABLE_EUREKA_SERVER = true;
		}

		// 判断是否标记了网关代理
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableGatewayProxy.class, config.getClass())) {
			ConfigWrap.ENABLE_GATEWAY_PROXY = true;
		}

		// 判断是否标记了注册动作
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableToEureka.class, config.getClass())) {
			ConfigWrap.ENABLE_TO_EUREKA = true;
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
			irisPlugins.add((IPlugin) obj);
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
