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
public class BuildProtocolBeanException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5178093335368924719L;

	public BuildProtocolBeanException(String message) {
		super(message);
	}

	public BuildProtocolBeanException(Throwable cause) {
		super(cause);
	}

	public BuildProtocolBeanException(String message, Throwable cause) {
		super(message, cause);
	}

}
