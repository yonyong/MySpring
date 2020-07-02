package top.yonyong.myspring.B_higher.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 16:13
 * @Version 1.0.0
 **/
public interface SpringAware {
    /**
     * 1.BeanNameAware  获得容器中bean的名称
     * 2.BeanFactoryAware   获得当前bean factory，这样可以调用容器的服务
     * 3.MessageSourceAware        获得message source，这样就可以获得文本信息
     * 4.ApplicationEventPublisherAware 应用时间发布器，可以发布事件，Spring event里面的DemoPublisher也可以实现这个接口来发布事件
     * 5.ResourceLoaderAware    获得资源加载器，可以获得外部资源文件
     * 6.ApplicationContextAware    获得当前application context，这样可以调用容器的服务，继承这个接口可以集合上面所有的功能，
     *                              获得spring容器所有的服务，但原则上还是用什么接口，就实现什么接口
     */


    @Service
    class AwareService implements BeanNameAware,ResourceLoaderAware{

        private String beanName;
        private ResourceLoader loader;

        @Override
        public void setBeanName(String s) {
            this.beanName = s;
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.loader = resourceLoader;
        }

        public void outputResult(){
            System.out.println("Bean的名称为：" + beanName);

            Resource resource = loader.getResource("classpath:docs/test.txt");
            try {
//                System.out.println("ResourceLoader加载的文件内容为：" + IOUtils.toString(resource.getInputStream()));
                System.out.println("ResourceLoader加载的文件内容为：" + resource.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    class MyApplicationAware implements ApplicationContextAware{

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        }
    }

    class MyBeanFactoryAware implements BeanFactoryAware {

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        }
    }

    class MyMessageSourceAware implements MessageSourceAware{

        @Override
        public void setMessageSource(MessageSource messageSource) {

        }
    }
    class MyApplicationEventPublisherAware implements ApplicationEventPublisherAware{

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        }
    }
}
