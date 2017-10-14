package cn.dazd.gateway.server;

import cn.dazd.gateway.server.config.GatewayServerConfig;
import cn.dazd.iris.core.config.ProtocolBootstrap;

public class GatewayServerRun {

	public static void main(String[] args) {
		new ProtocolBootstrap().run(new GatewayServerConfig());
	}

}
