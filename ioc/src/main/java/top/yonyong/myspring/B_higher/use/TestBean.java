package top.yonyong.myspring.B_higher.use;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.yonyong.myspring.B_higher.bean.MyBean;
import top.yonyong.myspring.B_higher.bean.SpringAware;
import top.yonyong.myspring.B_higher.bean.SpringTaskExectuor;
import top.yonyong.myspring.B_higher.bean.SpringTaskScheduler;
import top.yonyong.myspring.B_higher.config.HConfig;
import top.yonyong.myspring.B_higher.config.MyBeanConfig;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 16:31
 * @Version 1.0.0
 **/
public class TestBean {
    public static void main(String[] args) {
//        funSpringAware();

//        funTaskExecute();

//        funScheduledTask();

        funCreateBean();
    }

    /**
     * spring aware
     */
    private static void funSpringAware(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HConfig.class);
        SpringAware.AwareService awareService = context.getBean(SpringAware.AwareService.class);
        awareService.outputResult();
        context.close();
    }

    /**
     * 测试 spring 多线程
     */
    private static void funTaskExecute(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringTaskExectuor.TaskExectuctorConfig.class);

        SpringTaskExectuor.AsyncTaskService asyncTaskService =
                context.getBean(SpringTaskExectuor.AsyncTaskService.class);

        for (int i = 0; i < 10; i++) {
            asyncTaskService.ExecuteAsyncTask(i);
            asyncTaskService.ExecuteAsyncTaskPlus(i);
        }
        context.close();
    }

    /**
     * 测试 spring 计划任务
     */
    private static void funScheduledTask(){
//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext(SpringTaskScheduler.TaskExectuctorConfig.class);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringTaskScheduler.TaskExectuctorMyConfig.class);
//        context.close();
    }

    /**
     * 手动注入Bean容器
     */
    private static void funCreateBean(){
        //way1
//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext(MyBeanConfig.class);
//        context.register(MyBean.class);
//        final MyBean bean = context.getBean(MyBean.class);
//        bean.func();
//
        AnnotationConfigApplicationContext context2 =
                new AnnotationConfigApplicationContext(MyBeanConfig.class);

        ConfigurableApplicationContext configurableApplicationContext = context2;

        final ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();

        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        //way2.1
        defaultListableBeanFactory.registerSingleton(MyBean.class.getName(),new MyBean());

        //way2.2
//        BeanDefinition beanDefinition = new RootBeanDefinition(MyBean.class);
//        defaultListableBeanFactory.registerBeanDefinition(MyBean.class.getName(),beanDefinition);
        final MyBean bean1 = context2.getBean(MyBean.class);
        bean1.func();
    }

}
