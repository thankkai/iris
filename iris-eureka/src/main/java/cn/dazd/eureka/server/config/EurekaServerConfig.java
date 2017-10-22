package cn.dazd.eureka.server.config;

import cn.dazd.iris.core.annotation.EnableEurekaServer;
import cn.dazd.iris.core.config.AbstarctProtocolConfig;
import cn.dazd.iris.core.plugin.PluginCollector;

@EnableEurekaServer
public class EurekaServerConfig extends AbstarctProtocolConfig {

	@Override
	public void configPlugin(PluginCollector pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configProtocolXml(String configFileName) {
		// TODO Auto-generated method stub

	}

}
