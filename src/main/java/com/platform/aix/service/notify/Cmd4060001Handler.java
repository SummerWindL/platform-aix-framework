package com.platform.aix.service.notify;

import com.platform.aix.common.notify.IPgNotifyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Cmd4060001Handler
 * @Description
 * @Author yanl
 * @Date 2020/8/31 8:52
 * @Version 1.0
 **/
@Slf4j
public class Cmd4060001Handler implements IPgNotifyHandler {
    /**
     * 处理通知消息
     *
     * @param notifyno     命令号
     * @param notifytype   通知类型
     * @param notifyparams 参数
     */
    @Override
    public void handlerPgNotification(String notifyno, String notifytype, String notifyparams) {
        log.info("接收到 notifyno: {}, notifytype: {}, 参数 notifypaeams: {}",notifyno,notifytype,notifyparams);
    }
}
