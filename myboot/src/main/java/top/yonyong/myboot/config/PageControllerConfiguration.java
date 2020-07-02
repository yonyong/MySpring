package top.yonyong.myboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Describtion 实现pageontroller的功能，简化开发代码
 * @Author yonyong
 * @Date 2020/6/23 16:17
 * @Version 1.0.0
 **/
@Configuration
public class PageControllerConfiguration implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/404").setViewName("/404");
    }
}
