package com.platform.aix.service.notify;

import cn.hutool.log.StaticLog;
import com.platform.aix.common.notify.impl.PostgresNotificationListener;
import org.springframework.stereotype.Service;

import static com.platform.aix.common.constants.Constants.POSTGRES_DESTINATION;

/**
 * @ClassName PgNotifyMessageAccepter
 * @Description
 * @Author yanl
 * @Date 2020/8/31 8:47
 * @Version 1.0
 **/
@Service
public class PgNotifyMessageAccepter implements Runnable {

    private PostgresNotificationListener pgNotificationListener;

    public void startPgNotificationListener(){
        StaticLog.info("\n-------- PgNotifyMessageAccepter [数据库XX通知服务] start --------");
        pgNotificationListener = new PostgresNotificationListener(POSTGRES_DESTINATION);
        if(pgNotificationListener != null){
            //1.启动监听目标线程
            pgNotificationListener.startListener();
            //2.处理目标 发送的notifyno任务
            pgNotificationListener.addHandler("cmd_4060001",new Cmd4060001Handler());
        }
    }

    @Override
    public void run() {
        this.startPgNotificationListener();
    }
}
