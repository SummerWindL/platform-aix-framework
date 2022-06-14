package com.platform.aix.demo;

import com.platform.aix.common.datacommon.db.domain.User;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Advance
 * @date 2022年05月23日 10:00
 * @since V1.0.0
 */
public class ThreadTest {
    private int count;
    public static String DEFAULT_USER = "system";
    private static ThreadLocal<DateFormat> yyyymmdd = new ThreadLocal<DateFormat>() {
        @Override
        protected synchronized DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    /**
     * 服务线程数据
     */
    private ThreadLocal<Map<String, Object>> serviceInfo = ThreadLocal.withInitial(HashMap::new);

    protected ThreadLocal<SimpleDateFormat> formatThreadLocal = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyMMddHHmmss"));

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new InheritableThreadLocal<>();

    @SneakyThrows
    public synchronized int incCount() {
        Thread.sleep(new Random().nextInt(500));
        return count++;
    }

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(threadTest.incCount())).start();
        }
    }

    public static void bindUser(User user) {
        if(user != null) {
            USER_THREAD_LOCAL.set(user);
        }
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
    public static String getUserName() {
        if(null != USER_THREAD_LOCAL.get()) {
            return USER_THREAD_LOCAL.get().getUsername();
        }
        return DEFAULT_USER;
    }
}
