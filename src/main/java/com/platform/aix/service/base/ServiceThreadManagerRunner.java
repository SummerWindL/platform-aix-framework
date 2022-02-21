package com.platform.aix.service.base;

import com.platform.aix.service.notify.PgNotifyMessageAccepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServiceThreadManagerRunner
 * @Description 所有业务线程管理器
 * @Author yanl
 * @Date 2020/8/31 8:45
 * @Version 1.0
 **/
@Component
public class ServiceThreadManagerRunner {
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private PgNotifyMessageAccepter pgNotifyMessageAccepter;

    public void submit(){
        //1、注册当前线程池，shutdown时回收处理
        ThreadPoolExecutorShutdownDefinition.registryExecutor(executor);
        executor.execute(pgNotifyMessageAccepter);
    }
}
