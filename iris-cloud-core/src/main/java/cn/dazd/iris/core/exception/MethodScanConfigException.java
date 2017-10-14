/**
 * 
 */
package cn.dazd.iris.core.exception;

/**
 * 搜索方法配置错误
 * 
 * @author Administrator
 *
 */
public class MethodScanConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5178093335368924719L;

	public MethodScanConfigException(String message) {
		super(message);
	}

	public MethodScanConfigException(Throwable cause) {
		super(cause);
	}

	public MethodScanConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
