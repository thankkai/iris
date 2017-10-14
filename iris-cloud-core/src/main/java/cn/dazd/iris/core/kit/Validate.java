package cn.dazd.iris.core.kit;

import java.util.Collection;
import java.util.Map;

/**
 * 自定义空对象判断
 * 
 * @author Administrator
 *
 */
public class Validate {

	public static boolean isNullOrEmpty(Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}

	public static boolean isNullOrEmpty(@SuppressWarnings("rawtypes") Map map) {
		return null == map || map.isEmpty();
	}

	public static boolean isNull(@SuppressWarnings("rawtypes") Map map) {
		return null == map;
	}

	public static boolean isNullOrEmpty(String value) {
		return null == value || value.isEmpty();
	}

	public static boolean isNullOrEmptyOrAllSpace(String value) {
		return null == value || value.trim().isEmpty();
	}

}
