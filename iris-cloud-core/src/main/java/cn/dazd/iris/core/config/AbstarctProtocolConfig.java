/**
 * 
 */
package cn.dazd.iris.core.config;

import cn.dazd.iris.core.plugin.PluginCollector;

/**
 * @author Administrator
 *
 */
public abstract class AbstarctProtocolConfig {

	// 初始化插件
	public abstract void configPlugin(PluginCollector pc);

	// 设置配置文件
	public abstract void configProtocolXml(String configFileName);
}
