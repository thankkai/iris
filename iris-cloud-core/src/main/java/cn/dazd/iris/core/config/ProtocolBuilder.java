/**
 * 
 */
package cn.dazd.iris.core.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uber.tchannel.api.TChannel;

import cn.dazd.iris.core.dto.HostConfigDTO;
import cn.dazd.iris.core.exception.MethodScanConfigException;
import cn.dazd.iris.core.exception.TchannelUnknownException;
import cn.dazd.iris.core.kit.ApiRegistryKits;
import cn.dazd.iris.core.kit.HostConfigKits;
import cn.dazd.iris.core.kit.PropertiesUtil;
import cn.dazd.iris.core.kit.SpiInstantiationKits;

/**
 * @author Administrator
 *
 */
final public class ProtocolBuilder {

	private final static Logger l = Logger.getLogger("ProtocolBuilder");
	private static TChannel tChannel = null;

	public static TChannel gettChannel() {
		if (tChannel == null) {
			throw new TchannelUnknownException("it must not be null.");
		}
		return tChannel;
	}

	private static ReadWriteLock rwl = new ReentrantReadWriteLock();

	private ProtocolBuilder() {

	}

	/**
	 * 构建整个协议环境
	 */
	public static void build() {
		rwl.writeLock().lock();
		try {
			tChannel = createServer();

			// 是否开启网关路由
			// if (ProtocolConfig.ConfigWrap.enableGatewayProxy) {
			// tChannel.getServerBootstrap().childHandler(ChannelInitializerExt.channelInitializer(true,
			// tChannel))
			// .validate();
			// 初始化网关API入口
			// GatewayRouteProp.init(tChannel);
			// }
			// 注册本地方法到tchannel中
			apiRegistry();

			// 实例化SPI接口类
			// spiInstantiation();
		} catch (RuntimeException e) {
			l.info(e.getMessage());
		} finally {
			rwl.writeLock().unlock();
		}
	}

	/**
	 * 创建服务实例
	 * 
	 * @return
	 */
	static TChannel createServer() {
		l.info("==> the tchannel server is readying ......");

		TChannel tChannel = null;

		try {

			HostConfigDTO hostConfig = HostConfigKits.getAppConfig();
			InetSocketAddress isa = new InetSocketAddress("0.0.0.0", hostConfig.getPort());
			tChannel = new TChannel.Builder(hostConfig.getServiceName()).setServerHost(isa.getAddress())
					.setServerPort(isa.getPort()).setClientMaxPendingRequests(Integer.MAX_VALUE).build();
			l.info(String.format("==> tchannel:%s | ip:%s | port:%s | prcessid:%s，the tchannel builded successfully.",
					hostConfig.getServiceName(), hostConfig.getIp(), hostConfig.getPort(), hostConfig.getProcessId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		l.info("==> the tchannel server created successfully ^_^ ");
		return tChannel;
	}

	/**
	 * api标记类里的方法注册到tchannel
	 */
	static void apiRegistry() {
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
	 * 将远程接口实例化
	 */
	static void spiInstantiation() {
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
			SpiInstantiationKits sik = new SpiInstantiationKits(property);
			sik.initRemoteClassInstance();
		} catch (Exception e) {
			l.info(e.getMessage());
		}
	}
}
