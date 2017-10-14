package cn.dazd.iris.core.kit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import cn.dazd.iris.core.annotation.AnnotationTypeFilter;
import cn.dazd.iris.core.annotation.ApiAnnotation;
import cn.dazd.iris.core.config.ProtocolBuilder;
import cn.dazd.iris.core.exception.IFaceUnimplementedException;
import cn.dazd.iris.core.exception.MethodInvokeException;
import cn.dazd.iris.core.tchannel.handler.ApiHandlerImpl;

/**
 * API接口注册器
 * 
 * @author Administrator
 *
 */
public class ApiRegistryKits {

	private final static String RESOURCE_PATTERN = "/**/*.class";

	private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private final List<String> packagesList = new LinkedList<String>();

	private final List<AnnotationTypeFilter> typeFilters = new LinkedList<AnnotationTypeFilter>();

	final Logger l = Logger.getLogger("ApiRegistryKits");

	/**
	 * 构造函数
	 * 
	 * @param packagesToScan
	 *            指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
	 * @param annotationFilter
	 *            指定扫描包中含有特定注解标记的bean,支持多个注解
	 */
	@SafeVarargs
	public ApiRegistryKits(List<String> packagesToScan, Class<? extends Annotation>... annotationFilter) {
		if (packagesToScan != null) {
			for (String packagePath : packagesToScan) {
				this.packagesList.add(packagePath);
			}
		}
		typeFilters.add(new AnnotationTypeFilter(ApiAnnotation.class, false));
		if (annotationFilter != null) {
			for (Class<? extends Annotation> annotation : annotationFilter) {
				typeFilters.add(new AnnotationTypeFilter(annotation, false));
			}
		}
	}

	public boolean initGlobalClassInstance()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
							Class<?> instanceType = org.apache.commons.lang3.ClassUtils
									.getClass(getClass().getClassLoader(), className, true);
							Class<?> service = instanceType.getAnnotation(ApiAnnotation.class).service();
							List<Class<?>> interfaces = org.apache.commons.lang3.ClassUtils
									.getAllInterfaces(instanceType);
							// 判断是否实现了iface接口
							boolean isiface = false;
							String ifaceClassName = new StringBuilder().append(service.getName()).append("$Iface")
									.toString();
							for (Class<?> faceclass : interfaces) {
								if (ifaceClassName.equals(faceclass.getName())) {
									isiface = true;
									break;
								}
							}
							if (!isiface) {
								throw new IFaceUnimplementedException("未实现当前注解中service的iface接口");
							}
							scanMethod(service, instanceType);
						}
					}
				}
				flag = true;
			}
		} catch (MethodInvokeException e) {
			e.printStackTrace();
			throw new MethodInvokeException(e);
		}
		return flag;
	}

	void scanMethod(Class<?> service, Class<?> instanceType) throws InstantiationException, IllegalAccessException {
		// 获取声明的方法，即API接口
		Method[] mt = instanceType.getDeclaredMethods();
		try {
			for (Method method : mt) {

				// 注册方法，方法名严格区分大小写
				ProtocolBuilder.gettChannel().makeSubChannel(service.getSimpleName()).register(
						new StringBuffer(service.getSimpleName()).append("::").append(method.getName()).toString(),
						new ApiHandlerImpl(service.getName(), instanceType.newInstance(), method));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
