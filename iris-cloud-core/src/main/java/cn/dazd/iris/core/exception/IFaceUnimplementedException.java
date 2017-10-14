/**
 * 
 */
package cn.dazd.iris.core.exception;

/**
 * 没有实现iface接口
 * 
 * @author Administrator
 *
 */
public class IFaceUnimplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5178093335368924719L;

	public IFaceUnimplementedException(String message) {
		super(message);
	}

	public IFaceUnimplementedException(Throwable cause) {
		super(cause);
	}

	public IFaceUnimplementedException(String message, Throwable cause) {
		super(message, cause);
	}

}
