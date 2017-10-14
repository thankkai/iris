/**
 * 
 */
package cn.dazd.iris.core.plugin;

/**
 * 插件接口
 * 
 * @author Administrator
 * 
 *
 */
public interface IPlugin {

	/**
	 * 执行
	 */
	boolean start();

	/**
	 * 结束
	 */
	boolean stop();
}
