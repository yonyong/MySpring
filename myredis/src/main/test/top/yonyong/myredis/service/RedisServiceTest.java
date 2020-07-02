package top.yonyong.myredis.service;

import org.junit.Test;
import top.yonyong.myredis.BaseTest;
import top.yonyong.myredis.entity.Person;
import top.yonyong.myredis.service.impl.OrderServiceImpl;
import top.yonyong.myredis.service.impl.RedisServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/29 10:38
 * @Version 1.0.0
 **/
public class RedisServiceTest extends BaseTest {

    @Resource
    RedisServiceImpl redisService;
    @Resource
    OrderServiceImpl orderService;
    private Person person2;

    @Test
    public void fun1() {
        redisService.fun();
    }

    @Test
    public void getOrderNo() {
        final String orderNoByRedis = orderService.getOrderNoByRedis("NO");
        System.out.println(orderNoByRedis);
    }

    @Test
    public void testStr() {
        redisService.funString();
    }

    @Test
    public void testList() {
        redisService.funList();
    }

    @Test
    public void testHash() {
        redisService.funHash();
    }

    @Test
    public void testSet() {
        redisService.funSet();
    }

    @Test
    public void testZset() {
        redisService.funZset();
    }

    @Test
    public void testHyperLogLog() {
        redisService.funHyperLogLog();
    }

    @Test
    public void testCluster() {
        redisService.funCluster();
    }

    @Test
    public void testGeo() {
        redisService.funGeo();
    }

    @Test
    public void testDotDotDot() {
        Person person = Person.builder()
                .uid(542121321)
                .age(23)
                .name("yonyong")
                .sex('男')
                .salary(BigDecimal.valueOf(1000000000))
                .build();
        Person person1 = person;
        Person person2 = person;
        Person person3 = person;
        person1 = person1.toBuilder()
                .name("small fei")
                .age(25)
                .salary(BigDecimal.valueOf(500000))
                .build();
        person2 = person2.toBuilder()
                .name("small xu")
                .age(24)
                .salary(BigDecimal.valueOf(1000000))
                .build();
        person3 = person3.toBuilder()
                .name("blue sky")
                .age(25)
                .salary(BigDecimal.valueOf(100))
                .build();
        redisService.test(person, person1, person2, person3);
    }
}
