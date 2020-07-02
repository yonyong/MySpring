package top.yonyong.myboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yonyong.myboot.myautoconfig.HelloService;

/**
 * @Describtion 实现springboot的自动装配 onconditional，应用场景，nacos配置中心，猜的
 * @Author yonyong
 * @Date 2020/6/23 15:44
 * @Version 1.0.0
 **/
@RestController
@SpringBootApplication
public class MyBootApplication {

    @Autowired
    HelloService helloService;

    @GetMapping("/")
    public String index(){
        return helloService.sayHello();
    }

    public static void main(String[] args) {
        SpringApplication.run(MyBootApplication.class,args);
    }
}
