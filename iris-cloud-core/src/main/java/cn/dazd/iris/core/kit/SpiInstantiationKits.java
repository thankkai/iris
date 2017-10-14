package cn.dazd.iris.core.kit;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import cn.dazd.iris.core.annotation.AnnotationTypeFilter;
import cn.dazd.iris.core.annotation.RemoteAnnotation;
import cn.dazd.iris.core.exception.MethodInvokeException;

public class SpiInstantiationKits {

	private final static String RESOURCE_PATTERN = "/**/*.class";

	private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private final List<String> packagesList = new LinkedList<String>();

	final Logger l = Logger.getLogger("SpiInstantiationKits");

	final static Map<String, Object> REMOTE_INSTANCE_CLASS_MAP = new Hashtable<String, Object>();

	public SpiInstantiationKits(List<String> packagesToScan) {
		if (packagesToScan != null) {
			for (String packagePath : packagesToScan) {
				this.packagesList.add(packagePath);
			}
		}
	}

	/**
	 * 获取实例化的接口
	 * 
	 * @param key
	 * @return
	 */
	public static Object getRemoteInstanceSPI(String key) {
		Object obj = null;
		if (REMOTE_INSTANCE_CLASS_MAP.containsKey(key)) {
			obj = REMOTE_INSTANCE_CLASS_MAP.get(key);
		}
		return obj;
	}

	public boolean initRemoteClassInstance() {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {

			if (!this.packagesList.isEmpty()) {
				for (String pkg : this.packagesList) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
					Resource[] resources = this.resourcePatternResolver.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
							this.resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory.getMetadataReader(resource);
							String className = reader.getClassMetadata().getClassName();
							if (className.contains("$")) {
								continue;
							}
							l.info(className);
							Class<?> instanceType = Class.forName(className);
							AnnotationTypeFilter atFilter = new AnnotationTypeFilter(RemoteAnnotation.class, false);
							boolean filterstate = AnnotationUtils
									.isAnnotationDeclaredLocally(atFilter.getAnnotationType(), instanceType);
							if (filterstate) {
								REMOTE_INSTANCE_CLASS_MAP.put(instanceType.getName(),
										new RemoteProxyView().newInstance(new Class[] { instanceType }));
							}

						}
					}

				}
				flag = true;
			}
		} catch (MethodInvokeException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	static class RemoteProxyView implements InvocationHandler {

		public Object newInstance(Class<?>[] target) {
			return Proxy.newProxyInstance(this.getClass().getClassLoader(), target, this);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String methodName = method.getName();
			System.out.println("调用的方法名称为:" + methodName);
			Class<?> returnType = method.getReturnType();
			System.out.println("返回的类型为" + returnType.getName());
			return ByteBuffer.wrap("执行完成！".getBytes());
		}
	}
}
