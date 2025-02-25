package com.platform.aix.common;

import cn.hutool.core.lang.copier.Copier;
import com.platform.aix.PlatformAixApplicationTests;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Advance
 * @date 2022年03月29日 17:38
 * @since V1.0.0
 */
public class LamTest extends PlatformAixApplicationTests {
//    @Autowired
//    private QueryMapper queryMapper;
//    @Autowired
//    private QueryTest queryTest;
    @Autowired
    private static StringEncryptor stringEncryptor;
    public static void main(String[] args) {
        //query(() -> stringEncryptor.encrypt("1"));
        Copier<Integer> integerCopier = () -> 5;
        System.out.println(integerCopier.copy());
        new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();

        String msgA = "Hello ";
        String msgB = "World ";
        System.out.println(
                getString(
                        () -> msgA + msgB
                )
        );

        List<String> collect = Stream.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
                .map(m -> "2023-" + m)
                .collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void test(){
//        queryTest.query();
    }



    private static String getString(Supplier<String> stringSupplier) {
        return stringSupplier.get();
    }
}
