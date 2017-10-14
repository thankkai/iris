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
public class TchannelUnknownException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5178093335368924719L;

	public TchannelUnknownException(String message) {
		super(message);
	}

	public TchannelUnknownException(Throwable cause) {
		super(cause);
	}

	public TchannelUnknownException(String message, Throwable cause) {
		super(message, cause);
	}

}
