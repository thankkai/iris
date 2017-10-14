package cn.dazd.order.search.server.api;

import java.nio.ByteBuffer;

import cn.dazd.iris.core.annotation.ApiAnnotation;

public class OrderSearchAPI {

	public ByteBuffer searchBySample(ByteBuffer args) {
		String str = new String("ordersearch is good!");
		return ByteBuffer.wrap(str.getBytes());
	}

	public ByteBuffer searchBySample2(ByteBuffer args) {
		String str = new String("ordersearch is good!");
		return ByteBuffer.wrap(str.getBytes());
	}
}
