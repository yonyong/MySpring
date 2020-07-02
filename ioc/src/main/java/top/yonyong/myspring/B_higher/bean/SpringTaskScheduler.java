package top.yonyong.myspring.B_higher.bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 17:31
 * @Version 1.0.0
 **/
public interface SpringTaskScheduler {


    @Service
    class scheduledTaskService{
        public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

        @Scheduled(fixedRate = 5000)
        public void reportCurrentTime(){
            System.out.println("每隔五秒执行一次： " + SIMPLE_DATE_FORMAT.format(new Date()));
        }

        @Scheduled(cron = "0 28 11 ? * *")  //每天11点28分
        public void fixTimeExecution(){
            System.out.println("在指定时间执行： " + SIMPLE_DATE_FORMAT.format(new Date()));
        }
    }

    @Configuration
    @ComponentScan
    @EnableScheduling
    class TaskExectuctorConfig{
    }

    @MyConfiguration
    @EnableScheduling
    class TaskExectuctorMyConfig{
    }
}
