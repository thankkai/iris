package cn.dazd.eureka.server;

import cn.dazd.eureka.server.config.EurekaServerConfig;
import cn.dazd.iris.core.config.ProtocolBootstrap;

public class EurekaServerRun {

	public static void main(String[] args) {
		new ProtocolBootstrap().run(new EurekaServerConfig());
	}

}
