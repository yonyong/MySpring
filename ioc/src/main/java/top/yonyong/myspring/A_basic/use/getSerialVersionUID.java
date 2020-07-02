package top.yonyong.myspring.A_basic.use;

import top.yonyong.myspring.A_basic.bean.Event;

import java.io.ObjectStreamClass;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2020/6/22 11:19
 * @Version 1.0.0
 **/
public class getSerialVersionUID {

    public static void main(String[] args) {
        funGetUUID(Event.DemoEvent.class);
    }

    /**
     * get uuid
     * @param c
     */
    public static void funGetUUID(Class c) {
        final ObjectStreamClass lookup = ObjectStreamClass.lookup(c);
        long seriaId = lookup.getSerialVersionUID();
        System.out.println(seriaId);

    }
}
