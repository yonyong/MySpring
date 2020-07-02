package top.yonyong.myredis.common.redis.zset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import top.yonyong.myredis.entity.Person;

/**
 * @Describtion zet 根据score排序
 * @Author yonyong
 * @Date 2020/7/1 16:29
 * @Version 1.0.0
 **/
@AllArgsConstructor
@NoArgsConstructor
public class PersonVoSet implements ZSetOperations.TypedTuple {
    Person person;
    double score;

    @Override
    public Object getValue() {
        return person;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        PersonVoSet personVoSet = (PersonVoSet) o;
        return new Double(this.score).compareTo(personVoSet.getScore());
    }
}
