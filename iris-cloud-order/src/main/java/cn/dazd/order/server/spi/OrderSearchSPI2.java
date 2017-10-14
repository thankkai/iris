package cn.dazd.order.server.spi;

import java.nio.ByteBuffer;

import cn.dazd.iris.core.annotation.RemoteAnnotation;

/**
 * 定义远程API接口,方法名与远程API方法名保持一致
 * 
 * @author Administrator
 *
 */

@RemoteAnnotation(server = "iris-cloud-order-search-1:30120", service = "ordersearch")
public interface OrderSearchSPI2 {

	/**
	 * 测试
	 * 
	 * @param args
	 * @return
	 */
	ByteBuffer searchBySample2(ByteBuffer args);
}
