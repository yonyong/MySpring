package top.yonyong.myspring.B_higher.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import javax.xml.stream.Location;
import java.lang.annotation.*;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/23 10:10
 * @Version 1.0.0
 **/
public interface HowToCreateBean {

    /**
     * 1.使用Spring XML方式配置，该方式用于在纯Spring 应用中，适用于简单的小应用，
     *  当应用变得复杂，将会导致XMl配置文件膨胀 ，不利于对象管理。
     * <bean id="xxxx"  class="xxxx.xxxx"/>
     */

    /**
     * 2.使用@Component,@Service,@Controler,@Repository注解

         这几个注解都是同样的功能，被注解的类将会被Spring 容器创建单例对象。

         @Component : 侧重于通用的Bean类

         @Service：标识该类用于业务逻辑

         @Controler：标识该类为Spring MVC的控制器类

         @Repository: 标识该类是一个实体类，只有属性和Setter,Getter
     */

    @Component
    class User{
    }

    /**
     * 3.使用@Bean注解,这种方式用在Spring Boot 应用中。

         @Configuration 标识这是一个Spring Boot 配置类，其将会扫描该类中是否存在@Bean 注解的方法，比如如下代码，将会创建User对象并放入容器中。

         @ConditionalOnBean 用于判断存在某个Bean时才会创建User Bean.
     */

    @Configuration
    class UserConfiguration{
        @Bean
        @ConditionalOnBean(Location.class)
        public User user(){
            return new User();
        }

    }

    /**
     * 4.使用注解@Import,也会创建对象并注入容器中
     */
    @Import(User.class)
    class MicroblogUserWebApplication {
        public static void main(String args[]) {
            SpringApplication.run(MicroblogUserWebApplication.class, args);
        }
    }

    /**
     * 5.使用ImportSelector或者ImportBeanDefinitionRegistrar接口，配合@Import实现。
        在使用一些Spring Boot第三方组件时，经常会看到@EnableXXX来使能相关的服务，这里以一个例子来实现。
     */

    @Slf4j
    class House {

        public void run(){

            log.info("House  run ....");
        }
    }

    @Slf4j
    class Person {


        public void run(){

            log.info("User  run ....");

        }

    }

    @Slf4j
    class Student {

        public void run(){

            log.info("Student  run ....");

        }

    }

    /**
     * 实现ImportSelector接口
     selectImports方法的返回值为需要创建Bean的类名称。这里创建Person类。
     */
    @Slf4j
    public class MyImportSelector implements ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata annotationMetadata) {


            log.info("MyImportSelector selectImports ...");
            return new String[]{
                    Person.class.getName()};
        }
    }

    /**
     * 实现ImportBeanDefinitionRegistrar接口
     beanDefinitionRegistry.registerBeanDefinition用于设置需要创建Bean的类名称。这里创建House类。
     */
    @Slf4j
    class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

            log.info("MyImportBeanDefinitionRegistrar  registerBeanDefinitions .....");
            BeanDefinition beanDefinition =  new RootBeanDefinition(House.class.getName());
            beanDefinitionRegistry.registerBeanDefinition(House.class.getName(),beanDefinition);
        }
    }

    /**
     * 创建一个配置类
     这里创建Student类。
     */
    @Configuration
    class ImportAutoconfiguration {

        @Bean
        public Student student(){
            return new Student();
        }
    }

    /**
     * 创建EnableImportSelector注解
     EnableImportSelector注解上使用@Import，引入以上的三个类。
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Target(ElementType.TYPE)
    @Import({MyImportSelector.class,ImportAutoconfiguration.class,MyImportBeanDefinitionRegistrar.class})
    public @interface EnableImportSelector {

        String value();

    }

    /**
     * 测试
     */
    @EnableImportSelector(value = "xxx")
    @SpringBootApplication
    class ImportDemoApplication {

        public static void main(String[] args) {
            ConfigurableApplicationContext context =  SpringApplication.run(ImportDemoApplication.class, args);

            Person user =  context.getBean(Person.class);
            user.run();

            Student student =  context.getBean(Student.class);
            student.run();

            House house =  context.getBean(House.class);
            house.run();

        }
    }

    /**
     * 6.手动注入Bean容器，有些场景下需要代码动态注入，以上方式都不适用。这时就需要创建 对象手动注入。

         通过DefaultListableBeanFactory注入。

         registerSingleton(String beanName,Object object);

         这里手动使用new创建了一个Location对象。并注入容器中。
     */
    @Component
    class LocationRegister implements BeanFactoryAware {

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory)beanFactory;
            //方式1
            //　Location location = new Location();
            //　listableBeanFactory.registerSingleton(Location.class.getName(),location);

            //方式2
            BeanDefinition locationBeanDefinition = new RootBeanDefinition(Location.class);
            listableBeanFactory.registerBeanDefinition(Location.class.getName(),locationBeanDefinition);

        }
    }

}
