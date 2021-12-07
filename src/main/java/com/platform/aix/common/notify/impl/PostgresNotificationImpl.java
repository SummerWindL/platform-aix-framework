package com.platform.aix.common.notify.impl;

import com.platform.aix.common.exception.AixException;
import com.platform.aix.common.notify.IPgNotifyAcceptHandler;
import com.platform.aix.common.notify.IPgNotifyHandler;
import com.platform.aix.common.notify.bean.CallableResult;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 事件监听处理器 分发不同的处理业务
 * @author: fuyl
 * @create: 2020-08-29 17:05
 **/

public class PostgresNotificationImpl implements Callable<CallableResult> {

    private static final Logger log = LoggerFactory.getLogger(PostgresNotificationImpl.class);

    private DataSource dataSource;
    private Connection conn;
    private PGConnection pgConn;
    private String target; //数据库监听地址
    private Statement selectStatement;
    private Map<String,Object> handleMap = new ConcurrentHashMap<>();

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
            log.info("初始化数据库监听地址成功：【{}】",target);
        } catch (SQLException e) {
            log.error("初始化监听地址失败：{}",e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public CallableResult call() {
        CallableResult result = new CallableResult();
        IPgNotifyAcceptHandler iPgNotifyAcceptHandler = new PgNotifyAcceptImpl();
        while(true){
            try
            {
                // issue a dummy query to contact the backend
                // and receive any pending notifications.
//                selectStatement = conn.createStatement();
//                ResultSet rs = selectStatement.executeQuery("SELECT 1");
//                rs.close();
//                selectStatement.close();

                PGNotification notifications[] = pgConn.getNotifications();
                /**
                 * 通知结果不为空才处理
                 * @author Advance
                 * @date 2021/12/3 10:40
                 * @return com.platform.aix.common.notify.bean.CallableResult
                 */
                if (notifications != null) {
                    //单条处理
                    for (PGNotification pgNotification : notifications) {
                        //1.接收并且为通知实体
                        PostgresNotice postgresNotice = iPgNotifyAcceptHandler.acceptNotification(pgNotification.getParameter());
                        //2.将通知分发给handler处理
                        handlerPgNotification(postgresNotice);
                        result.setCorrectFlag(true); //处理成功
                        result.setErrorMsg("成功");
                        result.setErrorCode(0);
                        //return result;
                    }
                }

                // wait a while before checking again
                Thread.sleep(30);
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
                result.setCorrectFlag(false); //处理失败
                result.setErrorCode(sqlException.getErrorCode());
                result.setErrorMsg(sqlException.getMessage());
                return result;
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
                result.setCorrectFlag(false); //处理失败
                result.setErrorCode(-1); //线程打断
                result.setErrorMsg(ie.getMessage());
                return result;
            }
        }
    }

    /**
     * 暴露的添加处理业务方法
     * @param notifyno
     * @param iPgNotifyHandler
     */
    public synchronized void addHandler(String notifyno, IPgNotifyHandler iPgNotifyHandler) {
        if (!StringUtils.hasText(notifyno) && !handleMap.containsKey(notifyno)) {
            throw new AixException("不存在的通知！"+notifyno);
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
