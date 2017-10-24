/**
 * 
 */
package cn.dazd.iris.core.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uber.tchannel.api.TChannel;
import com.uber.tchannel.messages.generated.Meta;

import cn.dazd.iris.core.config.ProtocolConfig.ConfigWrap;
import cn.dazd.iris.core.dto.HostConfigDTO;
import cn.dazd.iris.core.exception.MethodScanConfigException;
import cn.dazd.iris.core.kit.ApiRegistryKits;
import cn.dazd.iris.core.kit.HostConfigKits;
import cn.dazd.iris.core.kit.PropertiesUtil;
import cn.dazd.iris.core.kit.SpiInstantiationKits;
import cn.dazd.iris.core.router.RequestRouter;
import cn.dazd.iris.core.tchannel.handler.ToEurekaHandlerImpl;
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService;

/**
 * @author Administrator
 *
 */
final public class ProtocolBuilder {

	private final static Logger l = Logger.getLogger("ProtocolBuilder");
	private static TChannel tChannel = null;
	private final static ExecutorService EXECUTOR_SERVICE = new ForkJoinPool();

	final static String EUREKA_SERVICE = EurekaService.class.getSimpleName();
	final static String EUREKA_ENDPOINT = new StringBuffer(EUREKA_SERVICE).append("::").append("toEureka").toString();

	public static TChannel gettChannel() {
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

			// 启动注册中心
			if (ConfigWrap.ENABLE_EUREKA_SERVER) {
				tChannel.makeSubChannel(EUREKA_SERVICE).register(EUREKA_ENDPOINT, new ToEurekaHandlerImpl());
			}

			// 注册api接口
			endpointRegistry();
			// 注册SPI接口
			// spiRegistry();

			// 注册心跳检测
			String metaService = Meta.class.getSimpleName();
			tChannel.makeSubChannel(metaService).registerHealthHandler();

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
			TChannel.Builder builder = new TChannel.Builder(hostConfig.getServiceName()).setServerHost(isa.getAddress())
					.setServerPort(isa.getPort()).setClientMaxPendingRequests(Integer.MAX_VALUE);
			// 启用网关代理
			if (ConfigWrap.ENABLE_GATEWAY_PROXY) {
				builder.setExecutorService(EXECUTOR_SERVICE);
				tChannel = builder.build();
				tChannel.setCustomRequestRouter(new RequestRouter(tChannel, EXECUTOR_SERVICE));
			} else {
				tChannel = builder.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		l.info("==> the tchannel server created successfully ^_^ ");
		return tChannel;
	}

	/**
	 * api标记类里的方法注册到tchannel
	 */
	static void endpointRegistry() {
		for (String key : ProtocolConfig.ENDPOINT_HANDLER.keySet()) {
			String service = key
					.replaceFirst(new StringBuffer(ApiRegistryKits.ENDPOINT_SEPARATOR).append(".+").toString(), "");
			tChannel.makeSubChannel(service).register(key, ProtocolConfig.ENDPOINT_HANDLER.get(key));
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
