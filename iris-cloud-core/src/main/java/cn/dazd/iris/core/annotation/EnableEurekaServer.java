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
 * 仅在AbstarctProtocolConfig继承类上使用，表示成为注册中心
 * 
 * @author Administrator
 *
 */
public @interface EnableEurekaServer {

}
