package com.platform.aix.common.notify.impl;

import com.platform.aix.common.notify.PostgresNotificationRunner;
import com.platform.aix.common.notify.bean.CallableResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @description: 真正的启动线程类
 * @author: fuyl
 * @create: 2020-08-29 17:02
 **/
@Service
public class PostgresNotifyTask implements PostgresNotificationRunner {

    Logger log = LogManager.getLogger(getClass());
    //经过线程池管理多线程
    ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    public Future<CallableResult> run(Callable<CallableResult> callable) {
        PostgresNotificationImpl impl = (PostgresNotificationImpl) callable;
        FutureTask<CallableResult> futureTask = new FutureTask<CallableResult>(impl);
        Future<CallableResult> future = (Future<CallableResult>) threadPool.submit(futureTask);
        return future;
    }
}
