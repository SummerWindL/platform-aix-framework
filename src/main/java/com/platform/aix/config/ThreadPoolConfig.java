package com.platform.aix.config;

import com.platform.aix.common.util.ThreadMdcUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName Thread
 * @Description 线程管理器配置
 * @Author yanl
 * @Date 2020/8/31 8:42
 * @Version 1.0
 **/
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    // 线程池长期维持的线程数
    @Value("${custom.thread-pool.core-pool-size: 8}")
    private int corePoolSize;
    // 线程数的上限
    @Value("${custom.thread-pool.maximum-pool-size: 10}")
    private static final int maximumPoolSize = 10;
    // 任务的排队队列
    @Value("${custom.thread-pool.capacity: 512}")
    private static final int capacity = 512;

    @Primary
    @Bean
    public ThreadPoolTaskExecutor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);//设置核心线程数
        executor.setMaxPoolSize(core*2 + 1);//设置最大线程数
        executor.setKeepAliveSeconds(3);//除核心线程外的线程存活时间
        executor.setQueueCapacity(40);//如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setThreadNamePrefix("thread-execute");//线程名称前缀
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//设置拒绝策略
        return executor;
    }

    @Bean
    public ExecutorService aixDrawExecutor() {
        ThreadMdcUtil.ThreadPoolTaskExecutorMdcWrapper executor = new ThreadMdcUtil.ThreadPoolTaskExecutorMdcWrapper();
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(capacity);
        executor.setThreadNamePrefix("aix-draw-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
