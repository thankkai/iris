package cn.dazd.order.server;

import cn.dazd.iris.core.config.ProtocolBootstrap;
import cn.dazd.order.server.config.OrderServerConfig;

public class OrderServerRun {

	public static void main(String[] args) {
		new ProtocolBootstrap().run(new OrderServerConfig());
	}

}
