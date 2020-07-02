package top.yonyong.myspring.A_basic.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 10:26
 * @Version 1.0.0
 **/
@Service
public class A {
    @Autowired
    B b;

    public String say(){
        return b.sayHello();
    }
}

@Service
class B{
    public String sayHello(){
        return "hello";
    }
}
