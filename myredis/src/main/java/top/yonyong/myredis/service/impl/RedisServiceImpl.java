package top.yonyong.myredis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import top.yonyong.myredis.common.constant.RedisConstant;
import top.yonyong.myredis.common.redis.zset.PersonVoSet;
import top.yonyong.myredis.entity.Person;
import top.yonyong.myredis.service.RedisService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author yonyong
 * @Date 2020/6/29 10:21
 * @Version 1.0.0
 **/
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Resource
    RedisTemplate redisTemplate;

    private Person yonyong = Person.builder()
            .uid(542121321)
            .age(23)
            .name("yonyong")
            .sex('男')
            .salary(BigDecimal.valueOf(10000))
            .build();
    private Person smallfei = yonyong,
            smallxu = yonyong,
            bluesky = yonyong;

    @SuppressWarnings("unchecked")
    public Object fun() {
        String value = "hello";
        Set set = new HashSet();
        set.add(111);
        set.add(222);
        set.add("shit");
        redisTemplate.opsForValue().set(RedisConstant.STRING_KEY, "value", 1, TimeUnit.HOURS);
        final ValueOperations valueOperations = redisTemplate.opsForValue();
        final SetOperations setOperations = redisTemplate.opsForSet();
        return null;
    }

    @SuppressWarnings("unchecked")
    public void funString() {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        final ValueOperations valueOperations = redisTemplate.opsForValue();
        String strA = "holly shit";
        valueOperations.set(RedisConstant.STRING_STRA, strA);
//        valueOperations.set(RedisConstant.STRING_STRB,person);
        valueOperations.set(RedisConstant.STRING_STRC, "10");
        valueOperations.increment(RedisConstant.STRING_STRC, 10);
    }

    /**
     * 列表的头部（左边）或者尾部（右边）
     */
    @SuppressWarnings("unchecked")
    public void funList() {
        final ListOperations listOperations = redisTemplate.opsForList();
        /**默认push的方式是left  返回总个数
         * 放1 ： 1
         * 放2： 2 1
         * 放3 ： 3 2 1
         * 放4 ： 4 3 2 1
         */
        listOperations.leftPushAll(RedisConstant.LIST_LEFT, yonyong, smallfei, smallxu, bluesky);
        /**right的push 返回总个数
         * 放1 ： 1
         * 放2： 1 2
         * 放3 ： 1 2 3
         * 放4 ： 1 2 3 4
         */
        final Long pushCount = listOperations.rightPushAll(RedisConstant.LIST_RIGHT, yonyong, smallfei, smallxu, bluesky);
        //跟leftPush是同样的操作，唯一的不同是，当且仅当key存在时，才会更新key的值。如果key不存在则不会对数据进行任何操作。 返回总个数

        final Long listLeft1 = listOperations.leftPushIfPresent(RedisConstant.LIST_LEFT, yonyong);
        //删除集合左边第一个元素 返回被删除的元素
        final Person listLeft = (Person) listOperations.leftPop(RedisConstant.LIST_LEFT);
        //在2分钟里移除集合中左边的第一个元素，返回被删除的元素. 如果超过等待的时间仍没有元素则退出。
        final Object list = listOperations.leftPop(RedisConstant.LIST_LEFT, 2, TimeUnit.MINUTES);
        //从左往右删 2个 value为 person的 返回删除的个数
        final Long deletCount1 = listOperations.remove(RedisConstant.LIST_LEFT, 2, yonyong);
        //从右往左删 2个 value为 person的 返回删除的个数
        final Long deletCount2 = listOperations.remove(RedisConstant.LIST_LEFT, -1, yonyong);
        //删除所有 value为 person的 返回删除的个数
        final Long deletCount3 = listOperations.remove(RedisConstant.LIST_LEFT, 0, yonyong);
        //只保留从左至右第2个元素到第四个元素，其他元素全部删除 无返回值(void)
        listOperations.trim(RedisConstant.LIST_RIGHT, 1, 3);

        //获取listLeft集合中从左至右二个元素
        final Person getPerson = (Person) listOperations.index(RedisConstant.LIST_LEFT, 1);
        final List<Person> listLeft2 = listOperations.range(RedisConstant.LIST_LEFT, 0, 100);
        //返回listRight集合的元素个数
        final Long totalCount = listOperations.size(RedisConstant.LIST_RIGHT);

        //设置第1个为smallxu  无返回值
        listOperations.set(RedisConstant.LIST_RIGHT, 1, smallxu);

        listOperations.getOperations().delete(RedisConstant.LIST_LEFT);
        listOperations.getOperations().delete(RedisConstant.LIST_RIGHT);
    }

    @SuppressWarnings("unchecked")
    public void funHash() {
        final HashOperations hashOperations = redisTemplate.opsForHash();
        Map<String, Object> map = new HashMap<>();
        map.put("smallfei", smallfei);
        map.put("smallxu", smallxu);
        map.put("bluesky", bluesky);
        //put 没有返回值
        hashOperations.put(RedisConstant.HASH_MAP, "person", yonyong);
        hashOperations.putAll(RedisConstant.HASH_MAP1, map);

        //获取所有redis中map1中的键值对中的所有key
        final Set map1 = hashOperations.keys(RedisConstant.HASH_MAP1);
        //获取所有redis中map1中的键值对中的所有value
        final List map12 = hashOperations.values(RedisConstant.HASH_MAP1);
        //获取所有redis中map1中的键值对中的所有key和value
        final Map map11 = hashOperations.entries(RedisConstant.HASH_MAP1);
        final Object o = hashOperations.get(RedisConstant.HASH_MAP1, "smallfei");

    }

    @SuppressWarnings("unchecked")
    public void funSet() {
        final SetOperations setOperations = redisTemplate.opsForSet();
        Set set = new HashSet();
        //添加四个person示例到key为set的redis里 发明会添加的个数
        final Long addRows = setOperations.add(RedisConstant.SET_SET, yonyong, smallfei, smallxu);
        final Long addRows1 = setOperations.add(RedisConstant.SET_SET1, smallfei, smallxu, bluesky);

        //获得set 和 set1 不同value，返回不同value的集合
        final Set<Person> diffSet = setOperations.difference(RedisConstant.SET_SET, RedisConstant.SET_SET1);
        //获得set 和 set1 不同value，并将不同value的集合存入到key为diffSet的redis里，返回不同value的数量
        final Long aLong = setOperations.differenceAndStore(RedisConstant.SET_SET, RedisConstant.SET_SET1, RedisConstant.SET_SET_DIFF);

        //获得set 和 set1 相同的alue，返回相同的value的集合
        final Set intersect = setOperations.intersect(RedisConstant.SET_SET, RedisConstant.SET_SET1);
        //获得set 和 set1 相同的value，并将相同的value的集合存入到key为sameSet的redis里，返回相同的value的数量
        final Long aLong1 = setOperations.intersectAndStore(RedisConstant.SET_SET, RedisConstant.SET_SET1, RedisConstant.SET_SET_SAME);
        //获得sameSet的value集合
        final Set sameSet = setOperations.members(RedisConstant.SET_SET_SAME);
    }

    @SuppressWarnings("unchecked")
    public void funZset() {
        final ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //按薪水排序 方式一 一个一个塞
        final Boolean addStatus = zSetOperations.add(RedisConstant.ZSET_SET, yonyong, yonyong.getSalary().doubleValue());
        final Boolean add1 = zSetOperations.add(RedisConstant.ZSET_SET, smallfei, smallfei.getSalary().doubleValue());
        final Boolean add2 = zSetOperations.add(RedisConstant.ZSET_SET, smallxu, smallxu.getSalary().doubleValue());
        final Boolean add3 = zSetOperations.add(RedisConstant.ZSET_SET, bluesky, bluesky.getSalary().doubleValue());

        //按薪水排序 方式二 塞集合 这里需要一个PersonVoSet实现ZSetOperations.TypedTuple接口，重写compare方法，向下造型，不需要重写hashcode和equals
        Set<ZSetOperations.TypedTuple> set2 = new HashSet();
        ZSetOperations.TypedTuple item = new PersonVoSet(yonyong,yonyong.getSalary().doubleValue());
        ZSetOperations.TypedTuple item1 = new PersonVoSet(smallfei,smallfei.getSalary().doubleValue());
        ZSetOperations.TypedTuple item2 = new PersonVoSet(smallxu,smallxu.getSalary().doubleValue());
        ZSetOperations.TypedTuple item3 = new PersonVoSet(bluesky,bluesky.getSalary().doubleValue());
        set2.add(item);
        set2.add(item1);
        set2.add(item2);
        set2.add(item3);
        zSetOperations.add(RedisConstant.ZSET_SET1,set2);

        //两种添加之后的结果都是 smallfei(-1500) bluesky(5000) smallxu(7000) yonyong(10000) 从小到大依次排序
        //给yonyong 涨薪 10000元 返回涨薪后的工资
        final Double score = zSetOperations.incrementScore(RedisConstant.ZSET_SET1, yonyong, 10000);
        //返回yonyong的薪资排名，因为是最高，从零开始，排第三， 返回的是排名3
        final Long rank = zSetOperations.rank(RedisConstant.ZSET_SET, yonyong);

        //返回ZSET_SET 从0-100的所有集合 不带有score
        final Set range = zSetOperations.range(RedisConstant.ZSET_SET, 0, 100);
        //返回薪水5000-1000的所有集合，min和max两边都是闭区间 不带有score
        final Set set = zSetOperations.rangeByScore(RedisConstant.ZSET_SET, 5000, 10000);
        //返回薪水5000-1000的集合 tempSet，从tempSet中的第0个开始，取2两数据，返回这两条数据的集合 不带有score
        final Set set1 = zSetOperations.rangeByScore(RedisConstant.ZSET_SET, 5000, 10000, 0, 2);

        //返回ZSET_SET 从0-100的所有集合 带有score
        final Set set3 = zSetOperations.rangeWithScores(RedisConstant.ZSET_SET, 0, 100);
        //返回薪水5000-1000的所有集合，min和max两边都是闭区间 带有score
        final Set set4 = zSetOperations.rangeByScoreWithScores(RedisConstant.ZSET_SET, 5000, 10000);
        //返回薪水5000-1000的集合 tempSet，从tempSet中的第0个开始，取2两数据，返回这两条数据的集合 带有score
        final Set set5 = zSetOperations.rangeByScoreWithScores(RedisConstant.ZSET_SET, 5000, 10000, 0, 2);

        //返回yonyong排序的相反，yonyong的薪水为最高10000，rank为3 现在为对应的相反最低 0
        final Long aLong = zSetOperations.reverseRank(RedisConstant.ZSET_SET, yonyong);
        //将ZSET_SET中的第0-100个元素倒置，返回倒置后的ZSET_SET 不带score
        final Set set6 = zSetOperations.reverseRange(RedisConstant.ZSET_SET, 0, 100);
        final Set set7 = zSetOperations.reverseRangeWithScores(RedisConstant.ZSET_SET, 0, 100);

        //返回薪资在5000-10000的个数 两个都是闭区间
        final Long count = zSetOperations.count(RedisConstant.ZSET_SET, 5000, 10000);

    }

    /**
     *  是用来做基数统计的算法  统计一批数据中的不重复元素的个数
     */
    public void funHyperLogLog() {
        final HyperLogLogOperations hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
        System.out.println(hyperLogLogOperations.add(RedisConstant.HyperLogLog_A, yonyong, smallfei, smallxu, smallfei, yonyong, bluesky));
        //print 4
        System.out.println(hyperLogLogOperations.size(RedisConstant.HyperLogLog_A));
    }

    /**
     * 空间操作
     */
    public void funGeo() {
        final GeoOperations geoOperations = redisTemplate.opsForGeo();
    }

    /**
     * 集群操作
     */
    public void funCluster() {
        final ClusterOperations clusterOperations = redisTemplate.opsForCluster();
    }

    /**
     * 测试无限输入参数
     *
     * @param persons
     */
    public void test(Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            Person person = persons[i];
            System.out.println(person.getName());
        }
    }

    /**
     * 当bean创建完成的时候，会后置执行@PostConstruct修饰的方法
     */
    @PostConstruct
    public void initBean() {
        smallfei = smallfei.toBuilder()
                .name("small fei")
                .age(25)
                .salary(BigDecimal.valueOf(-1500))
                .build();
        smallxu = smallxu.toBuilder()
                .name("small xu")
                .age(24)
                .salary(BigDecimal.valueOf(7000))
                .build();
        bluesky = bluesky.toBuilder()
                .name("blue sky")
                .age(25)
                .salary(BigDecimal.valueOf(5000))
                .build();
    }

    /**
     * bean销毁前
     */
    @PreDestroy
    @SuppressWarnings("unchecked")
    public void destoryBean() throws IllegalAccessException {
        final Class<RedisConstant> redisConstantClass = RedisConstant.class;
        final Field[] declaredFields = redisConstantClass.getDeclaredFields();
        for (Field field : declaredFields) {
            final String key = (String) field.get(RedisConstant.class);
            final Boolean delete = redisTemplate.delete(key);
            if (delete) {
                System.out.println("delete redis key: " + key + " status: success!");
            } else {
                //not exist or ex
                System.err.println("delete redis key: " + key + " status: fail!");
            }
        }
    }
}
