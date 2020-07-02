package top.yonyong.myspring.A_basic.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 10:53
 * @Version 1.0.0
 **/
public interface BeanScope {

    @Service    //默认为singleton
    class BeanSingleton{

    }

    @Service
    @Scope("prototype")
    class BeanPrototype{

    }
}

