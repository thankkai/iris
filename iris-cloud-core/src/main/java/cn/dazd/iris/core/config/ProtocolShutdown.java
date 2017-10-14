package cn.dazd.iris.core.config;

import java.util.logging.Logger;

public class ProtocolShutdown extends Thread {

	final Logger l = Logger.getLogger(getClass().getName());

	@Override
	public void run() {
		l.info("the protocol server is closing.");
		if (null != ProtocolBuilder.gettChannel()) {
			ProtocolBuilder.gettChannel().shutdown(true);
		}
		l.info("the protocol server closed sucessfully.");
	}

}
