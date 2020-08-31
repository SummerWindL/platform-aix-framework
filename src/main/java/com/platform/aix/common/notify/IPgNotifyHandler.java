package com.platform.aix.common.notify;


/**
 * @description: postgre通知接口 所有的数据库通知业务都需要实现该接口
 * @author: fuyl
 * @create: 2020-08-30 10:23
 **/

public interface IPgNotifyHandler {
    /**
     *
     * 处理通知消息
     * @param notifyno 命令号
     * @param notifytype 通知类型
     * @param notifyparams 参数
     */
    void handlerPgNotification(String notifyno, String notifytype, String notifyparams);
}
