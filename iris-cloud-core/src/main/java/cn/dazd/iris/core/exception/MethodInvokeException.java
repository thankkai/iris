/**
 * 
 */
package cn.dazd.iris.core.exception;

/**
 * 执行方法配置错误
 * 
 * @author Administrator
 *
 */
public class MethodInvokeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5178093335368924719L;

	public MethodInvokeException(String message) {
		super(message);
	}

	public MethodInvokeException(Throwable cause) {
		super(cause);
	}

	public MethodInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

}
