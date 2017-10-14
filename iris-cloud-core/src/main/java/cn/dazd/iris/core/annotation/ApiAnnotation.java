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
@Target({ ElementType.TYPE })
/**
 * 某个类被标记后，该类所定义的方法会成为api接口
 * 
 * @author Administrator
 *
 */
public @interface ApiAnnotation {

	/**
	 * thrift生成的service类
	 * 
	 * @return
	 */
	Class<?> service();

}
