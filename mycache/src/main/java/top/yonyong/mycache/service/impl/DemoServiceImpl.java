package top.yonyong.mycache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.yonyong.mycache.dao.PersonRepository;
import top.yonyong.mycache.domain.Person;
import top.yonyong.mycache.service.DemoService;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/24 15:14
 * @Version 1.0.0
 **/
@Service
public class DemoServiceImpl implements DemoService{

    @Autowired
    PersonRepository personRepository;

    /**
     * 缓存里的值可能被改变时，需要及时的更新值
     * @param person
     * @return
     */
    @Override
    @CachePut(value = "people",key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、key为：" + person.getId() + "的数据做了缓存");
        return p;
    }

    @Override
    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了id、key为“" + id + "的数据缓存");
        personRepository.deleteById(id);
    }

    /**
     * 当重复使用相同的参数（key）访问此方法，不会走这个方法，而是直接走缓存
     * @param person
     * @return
     */
    @Override
    @Cacheable(value = "people",key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.findById(person.getId()).get();
        System.out.println("为id、key为：" + person.getId() + "的数据做了缓存");
        return p;
    }
}
