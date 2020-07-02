package top.yonyong.myboot.myautoconfig;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/23 15:37
 * @Version 1.0.0
 **/
public class HelloService {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String sayHello(){
        return "Hello" + msg;
    }
}
