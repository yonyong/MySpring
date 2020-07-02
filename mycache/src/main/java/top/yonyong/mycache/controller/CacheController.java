package top.yonyong.mycache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yonyong.mycache.domain.Person;
import top.yonyong.mycache.service.DemoService;

/**
 * @Author yonyong
 * @Date 2020/6/28 11:38
 * @Version 1.0.0
 **/
@RestController
public class CacheController {

    @Autowired
    DemoService demoService;

    @RequestMapping("/put")
    public Person put(Person person){
        return demoService.save(person);
    }

    @RequestMapping("/findOne")
    public Person findOne(Person person){
        return demoService.findOne(person);
    }

    @RequestMapping("/remove")
    public String remove(Long id){
        demoService.remove(id);
        return "ok";
    }

    @RequestMapping({"","/"})
    public String index(){
        return "index";
    }
}
