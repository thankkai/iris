package cn.dazd.iris.core.kit;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;


/**
 * xml文档解析器
 * @author Administrator
 *
 */
public final class XmlParser {
	public static Document load(String path, String encoding) throws Throwable {
		if (Validate.isNullOrEmptyOrAllSpace(path)) {
			throw new IllegalArgumentException("path");
		}
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path); // properties.load(Prop.class.getResourceAsStream(fileName));
			if (inputStream == null)
				throw new IllegalArgumentException("Properties file not found in classpath: " + path);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new InputStreamReader(inputStream, encoding));
			return doc;
		} catch (DocumentException | IOException e) {
			throw e;
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Node> analyze(Document doc, String tagName) {
		if (null == doc) {
			throw new IllegalArgumentException("doc");
		}

		if (Validate.isNullOrEmptyOrAllSpace(tagName)) {
			throw new IllegalArgumentException("tagName");
		}
		return doc.selectNodes(tagName);
	}

	public static Element toElement(Object node) {
		if (null == node) {
			throw new IllegalArgumentException("node");
		}
		return (Element) node;
	}

	public static Element analyzeSingle(Document doc, String tagName) {
		if (null == doc) {
			throw new IllegalArgumentException("doc");
		}
		if (Validate.isNullOrEmptyOrAllSpace(tagName)) {
			throw new IllegalArgumentException("tagName");
		}
		@SuppressWarnings("rawtypes")
		List nodes = analyze(doc, tagName);
		if (null == nodes || 0 == nodes.size())
			return null;
		@SuppressWarnings("rawtypes")
		Iterator it = nodes.iterator();
		return it.hasNext() ? (Element) it.next() : null;
	}

	public static String getAttributeValue(Element elt, String attributeName) {
		if (null == elt) {
			throw new IllegalArgumentException("elt");
		}
		if (Validate.isNullOrEmptyOrAllSpace(attributeName)) {
			throw new IllegalArgumentException("attributeName");
		}
		Attribute attr = elt.attribute(attributeName);
		if (null == attr)
			return null;
		return attr.getValue();
	}

	public static String getAttributeValue(Document doc, String tagName, String attributeName) {
		if (null == doc)
			throw new IllegalArgumentException("doc");
		if (Validate.isNullOrEmptyOrAllSpace(tagName))
			throw new IllegalArgumentException("tagName");
		if (Validate.isNullOrEmptyOrAllSpace(attributeName))
			throw new IllegalArgumentException("attributeName");
		return getAttributeValue(analyzeSingle(doc, tagName), attributeName);
	}

	public static boolean hasAttributes(Element elt) {
		if (null == elt)
			throw new IllegalArgumentException("elt");
		return null != elt.attributes() && 0 != elt.attributes().size();
	}

	public static String getNodeValue(Element elt) {
		if (null == elt)
			throw new IllegalArgumentException("elt");
		return elt.getText();
	}

	public static String getNodeValue(Document doc, String tagName) {
		if (null == doc)
			throw new IllegalArgumentException("doc");
		if (Validate.isNullOrEmptyOrAllSpace(tagName))
			throw new IllegalArgumentException("tagName");
		Element ele = analyzeSingle(doc, tagName);
		if (null == ele)
			return null;
		return ele.getText();
	}

	public static String getAttributeValue(Node node, String attributeName) {
		if (null == node)
			throw new IllegalArgumentException("node");
		if (Validate.isNullOrEmptyOrAllSpace(attributeName))
			throw new IllegalArgumentException("attributeName");
		return getAttributeValue((Element) node, attributeName);
	}

	public static String getSingleChildNodeValue(Element elt, String childTagName) {
		if (null == elt)
			throw new IllegalArgumentException("elt");
		if (Validate.isNullOrEmptyOrAllSpace(childTagName))
			throw new IllegalArgumentException("childTagName");
		Node chird = elt.selectSingleNode(childTagName);
		if (null == chird)
			return null;
		return chird.getStringValue();
	}

}
