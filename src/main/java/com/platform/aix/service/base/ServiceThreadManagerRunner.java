package com.platform.aix.service.base;

import com.platform.aix.service.notify.PgNotifyMessageAccepter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @ClassName ServiceThreadManagerRunner
 * @Description 所有业务线程管理器
 * @Author yanl
 * @Date 2020/8/31 8:45
 * @Version 1.0
 **/
@Slf4j
@Component
public class ServiceThreadManagerRunner {
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private PgNotifyMessageAccepter pgNotifyMessageAccepter;
    @Resource(name = "aixDrawExecutor")
    private ExecutorService executorService;

    public void submit(){
        //1、注册当前线程池，shutdown时回收处理
        ThreadPoolExecutorShutdownDefinition.registryExecutor(executor);
        executor.execute(pgNotifyMessageAccepter);
        ThreadPoolExecutorShutdownDefinition.registryExecutor(executorService);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                log.info("-------------启动测试线程fxbDrawExecutor-----------");
            }
        });
    }
}
