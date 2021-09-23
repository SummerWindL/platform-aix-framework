package com.platform.aix.common.notify.impl;

import com.platform.aix.common.exception.AixException;
import com.platform.aix.common.notify.IPgNotifyAcceptHandler;
import com.platform.aix.common.notify.IPgNotifyHandler;
import com.platform.aix.common.notify.bean.PostgresNotice;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 事件监听处理器 分发不同的处理业务
 * @author: fuyl
 * @create: 2020-08-29 17:05
 **/

public class PostgresNotificationImpl implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PostgresNotificationImpl.class);

    private DataSource dataSource;
    private Connection conn;
    private PGConnection pgConn;
    private String target; //数据库监听地址
    private Statement selectStatement;
    private Map<String,Object> handleMap = new HashMap<>();

    public PostgresNotificationImpl(DataSource dataSource,Connection conn,PGConnection pgConn,String target) {
        this.dataSource = dataSource;
        this.conn = conn;
        this.pgConn = pgConn;
        this.target = target;
        this.initListenerTarget();
    }

    /**
     * 初始化监听地址
     */
    private void initListenerTarget() {
        //初始化监听地址
        try {
            selectStatement = conn.createStatement();
            selectStatement.execute("LISTEN "+target);
            selectStatement.close();
            log.info("初始化数据库监听地址成功：{}",target);
        } catch (SQLException e) {
            log.error("初始化监听地址失败：{}",e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        IPgNotifyAcceptHandler iPgNotifyAcceptHandler = new PgNotifyAcceptImpl();
        while(true){
            try
            {
                // issue a dummy query to contact the backend
                // and receive any pending notifications.
                selectStatement = conn.createStatement();
                ResultSet rs = selectStatement.executeQuery("SELECT 1");
                rs.close();
                selectStatement.close();

                PGNotification notifications[] = pgConn.getNotifications();
                if (notifications != null) {
                    for (PGNotification pgNotification : notifications) {
                        //1.接收并且为通知实体
                        PostgresNotice postgresNotice = iPgNotifyAcceptHandler.acceptNotification(pgNotification.getParameter());
                        //2.将通知分发给handler处理
                        handlerPgNotification(postgresNotice);
                    }
                }

                // wait a while before checking again
                Thread.sleep(30);
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }

    /**
     * 暴露的添加处理业务方法
     * @param notifyno
     * @param iPgNotifyHandler
     */
    public synchronized void addHandler(String notifyno, IPgNotifyHandler iPgNotifyHandler) {
        if (StringUtils.isEmpty(notifyno) && handleMap.containsKey(notifyno)) {
            //TODO 还没想好抛出什么异常 2020年8月31日
            throw new AixException();
        }
        handleMap.put(notifyno, iPgNotifyHandler);
    }

    private synchronized void handlerPgNotification(PostgresNotice notice) {
        if (handleMap.containsKey(notice.getNotifyno())) {
            IPgNotifyHandler iPgNotifyHandler = (IPgNotifyHandler) handleMap.get(notice.getNotifyno());
            iPgNotifyHandler.handlerPgNotification(notice.getNotifyno(), notice.getNotifytype(),notice.getNotifyparam());
        }
    }

}
