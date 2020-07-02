package top.yonyong.myspring.A_basic.use;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.yonyong.myspring.A_basic.bean.A;
import top.yonyong.myspring.A_basic.bean.BeanScope;
import top.yonyong.myspring.A_basic.bean.Event;
import top.yonyong.myspring.A_basic.config.BConfig;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 10:33
 * @Version 1.0.0
 **/
public class TestBean {
    public static void main(String[] args) {
//        funAnno();

//        funCheckScope();

        funApplicationEvent();
    }

    /**
     * 1.获取bean的方式
     */
    private static void funAnno(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext
                = new AnnotationConfigApplicationContext(BConfig.class);
        A a = annotationConfigApplicationContext.getBean(A.class);
        System.out.println(a.say());
        annotationConfigApplicationContext.close();
    }

    /**
     * 2. bean的生命周期
     */
    private static void funCheckScope(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BConfig.class);
        final BeanScope.BeanSingleton s1 = context.getBean(BeanScope.BeanSingleton.class);
        final BeanScope.BeanSingleton s2 = context.getBean(BeanScope.BeanSingleton.class);

        final BeanScope.BeanPrototype p1 = context.getBean(BeanScope.BeanPrototype.class);
        final BeanScope.BeanPrototype p2 = context.getBean(BeanScope.BeanPrototype.class);

        System.out.println(s1.equals(s2));
        System.out.println(p1.equals(p2));

        context.close();
    }

    /**
     * 3.Spring AplicationEvent 事件监听
     */
    private static void funApplicationEvent(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BConfig.class);
        Event.DemoPublisher demoPublisher = applicationContext.getBean(Event.DemoPublisher.class);
        demoPublisher.publish("hello application enent");
        applicationContext.publishEvent(new Event.DemoEvent(TestBean.class,"1111"));
    }
}
