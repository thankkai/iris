/**
 * 
 */
package cn.dazd.iris.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
/**
 * 供代理调用方法的注解器
 * 
 * @author Administrator
 *
 */
public @interface ProxyAnnotation {
	/**
	 * 路由名称
	 * 
	 * @return
	 */
	String value();

	/**
	 * 远程主机名/IP:端口
	 * 
	 * @return
	 */
	String server();

	/**
	 * 版本号
	 * 
	 * @return
	 */
	String version() default "";

	/**
	 * 别名
	 * 
	 * @return
	 */
	String alias() default "";
}
