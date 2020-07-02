package top.yonyong.myspring.B_higher.bean;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 17:04
 * @Version 1.0.0
 **/
public interface SpringTaskExectuor {

    @Configuration
    @ComponentScan
    @EnableAsync
    class TaskExectuctorConfig implements AsyncConfigurer{

        @Override
        public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setQueueCapacity(25);
            taskExecutor.initialize();
            return taskExecutor;
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return null;
        }
    }

    @Service
    class AsyncTaskService{

        @Async //如果该注解在类级别，则表明该类所有的方法都是异步方法
        public void ExecuteAsyncTask(Integer i){
            System.out.println("执行异步任务：" + i);
        }

        @Async
        public void ExecuteAsyncTaskPlus(Integer i){
            System.out.println("执行异步任务+1：" + (i+1));
        }
    }
}
