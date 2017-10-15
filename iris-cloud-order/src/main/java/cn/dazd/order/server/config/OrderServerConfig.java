package cn.dazd.order.server.config;

import cn.dazd.iris.core.annotation.EnableGatewayProxy;
import cn.dazd.iris.core.config.AbstarctProtocolConfig;
import cn.dazd.iris.core.plugin.PluginCollector;

@EnableGatewayProxy
public class OrderServerConfig extends AbstarctProtocolConfig {

	@Override
	public void configPlugin(PluginCollector pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configProtocolXml(String configFileName) {
		// TODO Auto-generated method stub

	}

}
