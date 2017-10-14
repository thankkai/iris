package cn.dazd.iris.core.config;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Element;

import cn.dazd.iris.core.exception.BuildProtocolBeanException;
import io.netty.util.internal.ConcurrentSet;

/**
 * 处理协议配置的上下文
 * 
 * @author Administrator
 *
 */
public final class ProtocolContext {

	final static String PROTOCOL_CONFIG_XML = "protocol_config.xml";
	final static String PROTOCOLS_ROOT_NODE = "protocols";
	final static String PROTOCOL_NODE = "protocol";
	final static String CONTENT_METHOD_SCAN_NODE = "method-scan";
	final static Logger l = Logger.getLogger("cn.dazd.iris.protocol.config.ProtocolContext");

	final static Set<Object> objSet = new ConcurrentSet<Object>();

	/**
	 * 对protocol这类的节点做初始化
	 * 
	 * @param pnodeList
	 */
	static void initProtocals(List<Element> pnodeList) {
		for (Iterator<Element> iterator = pnodeList.iterator(); iterator.hasNext();) {
			Element ele = iterator.next();
			Object obj = buildProtocolBean(ele);
			ProtocolConfig.addPluginFromObj(obj);
		}
	}

	/**
	 * 构建运行的实例对象
	 * 
	 * @param ele
	 */
	@SuppressWarnings({ "unchecked" })
	static Object buildProtocolBean(Element ele) {
		String className = ele.attributeValue("class");
		Object clsObj = null;
		if (StringUtils.isBlank(className)) {
			throw new BuildProtocolBeanException("the property for the 'class' must not be null.");
		}
		List<Element> elist = ele.elements("property");
		try {
			Class<?> cls = Class.forName(className);
			clsObj = cls.newInstance();
			for (Iterator<Element> iterator = elist.iterator(); iterator.hasNext();) {
				Element e = iterator.next();
				String fieldName = e.attributeValue("name");
				String fieldValue = e.attributeValue("value");
				Field f = cls.getDeclaredField(fieldName);
				f.setAccessible(true);// 破坏性注入,不安全,后续要改掉（方案待定）
				f.set(clsObj, switchFieldValueType(fieldValue));
			}
			objSet.add(clsObj);
		} catch (ClassNotFoundException e1) {
			l.info(e1.getLocalizedMessage());
			throw new RuntimeException(e1.getLocalizedMessage());
		} catch (IllegalArgumentException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		} catch (SecurityException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		} catch (InstantiationException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		} catch (IllegalAccessException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		} catch (NoSuchFieldException e1) {
			throw new RuntimeException(e1.getLocalizedMessage());
		}
		return clsObj;
	}

	/**
	 * 转换字段类型
	 * 
	 * @param fieldValue
	 * @return
	 */
	static Object switchFieldValueType(String fieldValue) {
		if (NumberUtils.isDigits(fieldValue)) {
			return Integer.valueOf(fieldValue);
		} else if (NumberUtils.isCreatable(fieldValue)) {
			return Long.valueOf(fieldValue);
		}
		return fieldValue;
	}

	/**
	 * 查找某个类型的实例，返回单例对象
	 * 
	 * @param clz
	 * @return
	 */
	public static Object getInstance(Class<?> clz) {
		for (Iterator<Object> iterator = objSet.iterator(); iterator.hasNext();) {
			Object it = iterator.next();
			if (clz.isInstance(it)) {
				return it;
			}
		}
		return null;
	}
}
