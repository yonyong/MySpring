package top.yonyong.mycache.service;

import top.yonyong.mycache.domain.Person;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/24 15:13
 * @Version 1.0.0
 **/
public interface DemoService {

    Person save(Person person);

    void remove(Long id);

    Person findOne(Person person);
}
