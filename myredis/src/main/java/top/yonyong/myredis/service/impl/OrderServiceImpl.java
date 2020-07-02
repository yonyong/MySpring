package top.yonyong.myredis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @Describtion 订单号自增
 * @Author yonyong
 * @Date 2020/6/29 15:44
 * @Version 1.0.0
 **/
@Service
@Slf4j
public class OrderServiceImpl {
    @Resource
    private RedisTemplate redisTemplate;

    private static final String REDIS_INC_KEY = "REDIS_INC_KEY";

    /**
     * 用Redis计数器生成订单号,生成规则:前缀+年月日+Redis自增长序列号
     * 劣势：依赖于Redis,消耗带宽,消耗流量.
     * 优势：保证有序,而且每天的订单号增长都是从1开始自增的
     * @param prefix 前缀字符
     * @return 订单号 eg:NO201906110002
     */
    public String getOrderNoByRedis(String prefix){
        RedisAtomicLong idCounter = new RedisAtomicLong(REDIS_INC_KEY,redisTemplate.getConnectionFactory());
        Long increment = idCounter.getAndIncrement() + 1;
        Date today = new Date();
        final Calendar calendar = Calendar.getInstance();
        //设置凌晨过期
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        //设置第二天的凌晨 00:00 为失效期
        idCounter.expireAt(calendar.getTime());
        System.out.println("calendar = " + FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss").format(calendar.getTime()));

        //补四位，缺失的用0补位
        String importantKey = increment.toString();
        if (importantKey.length() < 4){
            importantKey = "0000".substring(0,4-importantKey.length()) + importantKey;
        }
        String orderNo = String.format("%s%s%s",prefix, FastDateFormat.getInstance("yyyyMMdd").format(today),importantKey);
        return orderNo;
    }
}
