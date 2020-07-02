package top.yonyong.myredis.common.constant;

/**
 * @Describtion store redis key
 * @Author yonyong
 * @Date 2020/7/1 15:41
 * @Version 1.0.0
 **/
public interface RedisConstant {
    /**
     * string
     */
    public static final String STRING_KEY = "key";
    public static final String STRING_STRA = "strA";
    public static final String STRING_STRB = "strB";
    public static final String STRING_STRC = "strC";

    /**
     * list
     */
    public static final String LIST_LEFT = "listLeft";
    public static final String LIST_RIGHT = "listRight";

    /**
     * hash
     */
    public static final String HASH_MAP = "map";
    public static final String HASH_MAP1 = "map1";

    /**
     * set
     */
    public static final String SET_SET = "set";
    public static final String SET_SET1 = "set1";
    public static final String SET_SET_DIFF = "diffSet";
    public static final String SET_SET_SAME = "sameSet";

    /**
     * zset
     */
    public static final String ZSET_SET = "zset";
    public static final String ZSET_SET1 = "zset1";

    /**
     * HyperLogLog
     */
    public static final String HyperLogLog_A = "HyperLogLog_A";
}
