package cn.dazd.order.search.server;

import cn.dazd.iris.core.config.ProtocolBootstrap;
import cn.dazd.order.search.server.config.OrderSearchServerConfig;

public class OrderSearchServerRun {

	public static void main(String[] args) {
		new ProtocolBootstrap().run(new OrderSearchServerConfig());
	}

}
