package cn.dazd.iris.core.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.annotation.AnnotationUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uber.tchannel.api.errors.TChannelError;
import com.uber.tchannel.api.handlers.RequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

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
import cn.dazd.iris.core.tchannel.thrift.eureka.EurekaService.toEureka_result;

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

	private static boolean TO_EUREKA_STATUS = true;

	static void initialize(AbstarctProtocolConfig config) {
		config.configPlugin(IRIS_PLUGINS);

		// 初始化本地需要注册的endpoint
		initEndpointPools();

		// 判断是否标记了注册动作
		if (AnnotationUtils.isAnnotationDeclaredLocally(EnableToEureka.class, config.getClass())) {
			ConfigWrap.ENABLE_TO_EUREKA = true;
			toEureka();
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

	/**
	 * 对注册中心进行连接，连接失败则启动失败
	 */
	private static void toEureka() {

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
		final ThriftRequest<toEureka_args> request = new ThriftRequest.Builder<toEureka_args>(
				ProtocolBuilder.EUREKA_SERVICE, ProtocolBuilder.EUREKA_ENDPOINT).setBody(args).build();

		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch threadLatch = new CountDownLatch(hcDTO.getZoneList().size());
		Executor executor = Executors.newFixedThreadPool(hcDTO.getZoneList().size());
		// 加载所有注册线程
		for (final EurekaZoneDTO obj : hcDTO.getZoneList()) {
			executor.execute(new RunnableWorker(startLatch, threadLatch, request, obj));
		}
		// 并发执行注册线程
		startLatch.countDown();
		try {
			threadLatch.await();
			if (TO_EUREKA_STATUS == false) {
				l.log(Level.SEVERE, "注册中心未连接成功，启动失败。");
				System.exit(0);
			} else {
				l.info("注册已完成");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行注册线程的内部静态类
	 * 
	 * @author Administrator
	 *
	 */
	static class RunnableWorker implements Runnable {
		private final static Logger l2 = Logger.getLogger(RunnableWorker.class.getName());
		final CountDownLatch startLatch;
		final CountDownLatch threadLatch;
		final ThriftRequest<toEureka_args> request;
		final EurekaZoneDTO obj;

		public RunnableWorker(final CountDownLatch startLatch, final CountDownLatch threadLatch,
				final ThriftRequest<toEureka_args> request, final EurekaZoneDTO obj) {
			this.startLatch = startLatch;
			this.threadLatch = threadLatch;
			this.request = request;
			this.obj = obj;
		}

		@SuppressWarnings("serial")
		@Override
		public void run() {
			try {
				startLatch.await();
				ThriftResponse<toEureka_result> future = RpcKits.sendRequest(request,
						new ArrayList<InetSocketAddress>() {
							{
								this.add(new InetSocketAddress(obj.getIp(), obj.getPort()));
							}
						});
				request.reset();
				if (null == future) {
					TO_EUREKA_STATUS = false;
				}
				l2.log(Level.SEVERE, "=====================>" + threadLatch.getCount());
				toEureka_result result = future.getBody(toEureka_result.class);
				if (result == null) {
					TO_EUREKA_STATUS = false;
				}
			} catch (InterruptedException | TChannelError | ExecutionException e) {
				l2.log(Level.SEVERE, e.getMessage());
			} finally {
				threadLatch.countDown();
				l2.log(Level.SEVERE, "=====================>" + threadLatch.getCount());
			}
		}

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
