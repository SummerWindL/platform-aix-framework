package com.platform.aix.common.listener.factory;

import com.platform.aix.config.DataLoaderConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * 建表接口
 * @author: Advance
 * @create: 2022-01-17 15:38
 * @since V1.0.0
 */
public interface CreateTable {
    /**
     * 建表
     * @author Advance
     * @date 2022/1/17 16:08
     * @param conn
     * @param metaData
     * @param dlc
     */
    void create(Connection conn, DatabaseMetaData metaData,DataLoaderConfiguration dlc) throws SQLException, IOException;
}
