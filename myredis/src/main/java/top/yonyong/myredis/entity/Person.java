package top.yonyong.myredis.entity;

import lombok.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/30 15:27
 * @Version 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Person implements Serializable{
    private static final long serialVersionUID = -2419233439523648417L;
    private long uid;
    private String name;
    private Integer age;
    private char sex;
    private BigDecimal salary;
}
