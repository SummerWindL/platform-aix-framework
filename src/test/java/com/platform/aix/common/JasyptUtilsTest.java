package com.platform.aix.common;

import cn.hutool.core.lang.Assert;
import com.platform.aix.PlatformAixApplicationTests;
import com.platform.common.util.JasyptUtils;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Advance
 * @date 2022年03月09日 17:00
 * @since V1.0.0
 */
public class JasyptUtilsTest extends PlatformAixApplicationTests {
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void test(){
        JasyptUtils.encrypt("哈哈哈哈");

        System.out.println(JasyptUtils.decrypt("AWwWlIH6R5suGVRphMThs4j6YewrqyBo"));
    }
    @Test
    public void assertTest(){
        String s1 = null;
        Assert.notNull(s1, "notNull，s1不能为空，当前为空已抛出异常");
    }

    @Test
    public void list(){
        List<Integer> list = new ArrayList() {
            {
                add(1);
                add(2);
                add(1);
            }
        };
        long count = list.stream().distinct().count();
        boolean isRepeat = count < list.size();
        System.out.println(count);//输出2
        System.out.println(isRepeat);//输出true
    }

    public static void main(String[] args) {
//        List<Integer> list = new ArrayList() {
//            {
//                add(1);
//                add(1);
//                add(1);
//            }
//        };
//        long count = list.stream().distinct().count();
//        boolean isRepeat = count == list.size();
//        System.out.println(count);//输出2
//        System.out.println(isRepeat);//输出true

        List<Integer> list = new ArrayList();
        list.add(1000);
        list.add(2000);
        AtomicLong entrustNum = new AtomicLong(0L);//委托数量 （T_INSTRUCTION）
        list.forEach(item ->{
            entrustNum.addAndGet(item.longValue());
        });
        System.out.printf("++++++:"+entrustNum);


    }
}
