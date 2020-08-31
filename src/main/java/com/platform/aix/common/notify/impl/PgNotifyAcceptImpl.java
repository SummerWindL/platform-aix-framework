package com.platform.aix.common.notify.impl;

import cn.hutool.json.JSONUtil;
import com.platform.aix.common.notify.IPgNotifyAcceptHandler;
import com.platform.aix.common.notify.bean.PostgresNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: default转换类
 * @author: fuyl
 * @create: 2020-08-30 10:45
 **/

public class PgNotifyAcceptImpl implements IPgNotifyAcceptHandler {
    private static Logger log = LoggerFactory.getLogger(PgNotifyAcceptImpl.class);
    /**
     * 接收postgres通知
     *
     * @return PostgresNotificationMsg
     */
    @Override
    public PostgresNotice acceptNotification(String notifyparam) {
        PostgresNotice notice = null;
        try{
            notice = JSONUtil.parseObj(notifyparam).toBean(PostgresNotice.class);
        }catch (Exception e){
            log.error("消息转换失败：{}",e.getMessage());
            e.printStackTrace();
        }

        return notice;
    }
}
