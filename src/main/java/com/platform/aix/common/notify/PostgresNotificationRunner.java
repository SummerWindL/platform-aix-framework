package com.platform.aix.common.notify;

import com.platform.aix.common.notify.bean.CallableResult;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @description: 执行接口
 * @author: fuyl
 * @create: 2020-08-29 10:56
 **/

public interface PostgresNotificationRunner {
    Future<CallableResult> run(Callable<CallableResult> callable);
}
