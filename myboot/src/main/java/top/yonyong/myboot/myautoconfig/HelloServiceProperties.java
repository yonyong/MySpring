package top.yonyong.myboot.myautoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/23 15:35
 * @Version 1.0.0
 **/
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {
    public static final String MSG = "world";

    private String msg = MSG;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
