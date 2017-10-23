package cn.dazd.iris.core.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.channel.ChannelFuture;

/**
 * 应用启动引导程序
 * 
 * @author Administrator
 *
 */
public class ProtocolBootstrap {

	final Logger l = Logger.getLogger(getClass().getName());

	/**
	 * 协议启动器
	 */
	public void run(AbstarctProtocolConfig aac) {
		try {
			ProtocolConfig.initialize(aac);
			ProtocolBuilder.build();
			ChannelFuture cf = ProtocolBuilder.gettChannel().listen();
			Runtime.getRuntime().addShutdownHook(new ProtocolShutdown());
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			l.log(Level.SEVERE, e.getLocalizedMessage());
		} finally {
			if (null != ProtocolBuilder.gettChannel()) {
				ProtocolBuilder.gettChannel().shutdown(true);
			}
		}
	}

}
