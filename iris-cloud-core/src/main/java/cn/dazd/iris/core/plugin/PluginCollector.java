package cn.dazd.iris.core.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 插件收集器
 * 
 * @author Administrator
 *
 */
public class PluginCollector {

	final List<IPlugin> pluginList = new ArrayList<IPlugin>();

	public List<IPlugin> getPluginList() {
		return pluginList;
	}

	public void add(IPlugin p) {
		pluginList.add(p);
	}

}
