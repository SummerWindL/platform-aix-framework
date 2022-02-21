package com.platform.aix.service.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Advance
 * @date 2022年02月18日 13:49
 * @since V1.0.0
 */
@Component
@Slf4j
public class ThreadPoolExecutorShutdownDefinition implements ApplicationListener<ContextClosedEvent> {

    private static final List<ExecutorService> POOLS = Collections.synchronizedList(new ArrayList<>(12));

    /**
     * 线程中的任务在接收到应用关闭信号量后最多等待多久就强制终止，其实就是给剩余任务预留的时间， 到时间后线程池必须销毁
     */
    private static final long awaitTermination = 60;

    /**
     * awaitTermination的单位
     */
    private static final TimeUnit timeUnit = TimeUnit.SECONDS;


    /**
     * 注册要关闭的线程池
     * 注意如果调用这个方法的话，而线程池又是由Spring管理的，则必须等待这个bean初始化完成后才可以调用
     * 因为依赖的{@link ThreadPoolTaskExecutor#getThreadPoolExecutor()}必须要在bean的父类方法中定义的
     * 初始化{@link ExecutorConfigurationSupport#afterPropertiesSet()}方法中才会赋值
     *
     * @param threadPoolTaskExecutor
     */
    public static void registryExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        POOLS.add(threadPoolTaskExecutor.getThreadPoolExecutor());
    }

    /**
     * 注册要关闭的线程池
     * 注意如果调用这个方法的话，而线程池又是由Spring管理的，则必须等待这个bean初始化完成后才可以调用
     * 因为依赖的{@link ThreadPoolTaskExecutor#getThreadPoolExecutor()}必须要在bean的父类方法中定义的
     * 初始化{@link ExecutorConfigurationSupport#afterPropertiesSet()}方法中才会赋值
     *
     * 重写了{@link ThreadPoolTaskScheduler#initializeExecutor(java.util.concurrent.ThreadFactory, java.util.concurrent.RejectedExecutionHandler)}
     * 来对父类的{@link ExecutorConfigurationSupport#executor}赋值
     *
     * @param threadPoolTaskExecutor
     */
    public static void registryExecutor(ThreadPoolTaskScheduler threadPoolTaskExecutor) {
        POOLS.add(threadPoolTaskExecutor.getScheduledThreadPoolExecutor());
    }

    /**
     * 注册要关闭的线程池， 如果一些线程池未交由线程池管理，则可以调用这个方法
     *
     * @param executor
     */
    public static void registryExecutor(ExecutorService executor) {
        POOLS.add(executor);
    }

    /**
     * 参考{@link org.springframework.scheduling.concurrent.ExecutorConfigurationSupport#shutdown()}
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("容器关闭前处理线程池优雅关闭开始, 当前要处理的线程池数量为: {} >>>>>>>>>>>>>>>>", POOLS.size());
        if (CollectionUtils.isEmpty(POOLS)) {
            return;
        }
        for (ExecutorService pool : POOLS) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(awaitTermination, timeUnit)) {
                    if (log.isWarnEnabled()) {
                        log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                    }
                }
            }
            catch (InterruptedException ex) {
                if (log.isWarnEnabled()) {
                    log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}

