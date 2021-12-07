package com.platform.aix.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 建表事件
 * @author Advance
 * @date 2021年11月19日 15:26
 * @since V1.0.0
 */
public class CreateTableEvent extends ApplicationEvent {
    @Autowired
    private DataSource dataSource;

    public CreateTableEvent(Object source) {
        super(source);
    }

    /**
     * 在初始化构造函数前执行
     * @author Advance
     * @date 2021/11/19 15:33
     */
    @PostConstruct
    public void init() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getSchema());
    }





}
