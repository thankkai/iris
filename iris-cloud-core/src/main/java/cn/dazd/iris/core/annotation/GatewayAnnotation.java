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
 * 供网关调用方法的注解器
 * 
 * @author Administrator
 *
 */
public @interface GatewayAnnotation {
	/**
	 * 路由名称
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 服务频道名称
	 * 
	 * @return
	 */
	String service();

	/**
	 * 版本号 格式:x.x.x(.yyMMdd_beta) 1.1.1 或 0.1.0.170331_beta
	 * 具体参考MethodVersionCode
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
