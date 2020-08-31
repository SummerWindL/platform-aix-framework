package com.platform.aix.common.notify.impl;

import com.platform.aix.common.notify.IPgNotifyHandler;
import com.platform.aix.common.notify.PostgresNotificationRunner;
import com.platform.aix.config.ServiceBeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.PGConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * @description: 数据库通知监听器
 * @author: fuyl
 * @create: 2020-08-29 10:52
 **/
@Slf4j
public class PostgresNotificationListener {

    private PostgresNotificationRunner runner;
    /**
     * 监听实现
     */
    private PostgresNotificationImpl notifyImpl;

    private DataSource dataSource ;
    private Connection conn;
    private PGConnection pgConn;
    private String target; //数据库监听地址

    public PostgresNotificationListener(String target) {
        this.dataSource = ServiceBeanConfig.dataSource;
        this.target = target;
        this.initListener();
    }

    public PostgresNotificationListener(PostgresNotificationRunner runner,String target) {
        this.runner = runner;
        this.dataSource = ServiceBeanConfig.dataSource;
        this.target = target;
        this.initListener();
    }

    private void initListener() {
        //初始化连接信息
        Connection connection = null;
        try {
            connection = dataSource.getConnection().getMetaData().getConnection();
        } catch (SQLException e) {
            //连接获取失败
            log.error("数据库连接获取失败：{}",e.getMessage());
            e.printStackTrace();
        }
        conn = connection;
        pgConn = (PGConnection) conn;
    }

    /**
     * 1.启动线程
     */
    public boolean startListener(){
        //1.初始化监听任务
        notifyImpl= new PostgresNotificationImpl(dataSource,conn,pgConn,target);
        //2.启动任务
        PostgresNotificationRunner runner = new PostgresNotifyTask();
        runner.run(notifyImpl);
        return true;
    }

    /**
     * 2.添加处理器
     * @param notifyno
     * @param iPgNotifyHandler
     */
    public void addHandler(String notifyno, IPgNotifyHandler iPgNotifyHandler){
        notifyImpl.addHandler(notifyno, iPgNotifyHandler);
    }


}
