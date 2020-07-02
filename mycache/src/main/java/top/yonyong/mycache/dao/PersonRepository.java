package top.yonyong.mycache.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yonyong.mycache.domain.Person;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/24 15:01
 * @Version 1.0.0
 **/
public interface PersonRepository extends JpaRepository<Person,Long> {
}
