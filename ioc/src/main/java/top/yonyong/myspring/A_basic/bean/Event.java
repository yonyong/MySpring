package top.yonyong.myspring.A_basic.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 11:11
 * @Version 1.0.0
 **/
public interface Event {

    /**
     * 消息产生 实体类 作传输
     */
    @Data
    class DemoEvent extends ApplicationEvent{

        private static final long SerialVersionUID = 3262689727055091833L;

        private String msg;

        public DemoEvent(Object source,String msg) {
            super(source);
            this.msg = msg;
        }
    }

    /**
     * 消息接收类 监听器
     */
    @Component
    @Async  //开启异步机制
    class DemoListener implements ApplicationListener<DemoEvent>{
        @Override
        public void onApplicationEvent(DemoEvent applicationEvent) {
            String msg = applicationEvent.getMsg();
            System.out.println("接收到消息：" + msg);
        }
    }

    /**
     * 消息发送 操作
     */
    @Component
    class DemoPublisher{
        @Autowired
        ApplicationContext applicationContext;

        public void publish(String msg){
            applicationContext.publishEvent(new DemoEvent(this,msg));
        }
    }
}
