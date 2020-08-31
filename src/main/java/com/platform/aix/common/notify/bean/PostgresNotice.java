package com.platform.aix.common.notify.bean;

import lombok.Data;

/**
 * @description: postgres通知消息体
 * @author: fuyl
 * @create: 2020-08-30 10:39
 **/
@Data
public class PostgresNotice {
    private String notifyno;    //通知命令号
    private String notifytype; //通知命令类型
    private String notifyparam; //参数
}
