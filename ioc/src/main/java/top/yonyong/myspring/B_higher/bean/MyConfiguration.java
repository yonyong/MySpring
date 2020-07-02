package top.yonyong.myspring.B_higher.bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @description 组合注解与元注解 MyConfiguration为组合注解
 * @author Justsafe
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan
public @interface MyConfiguration {
    String[] value() default {};
}
