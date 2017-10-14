package cn.dazd.iris.core.kit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * load properties
	 * 
	 * @param propertyFileName
	 * @return
	 */
	public static Properties loadProperties(String propertyFileName) throws Exception {
		Properties prop = new Properties();
		InputStreamReader in = null;
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFileName));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("加载属性文件失败，文件名：" + propertyFileName);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return prop;
	}

	public static String loadYaml(String yamlFilename) throws Exception {
		Yaml yaml = new Yaml();
		InputStreamReader reader = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(yamlFilename));
		String rdStr = yaml.load(reader).toString();
		reader.close();
		return rdStr;
	}
}
