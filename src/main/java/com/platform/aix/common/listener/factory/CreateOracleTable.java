package com.platform.aix.common.listener.factory;

import com.platform.aix.common.loader.ScriptRunner;
import com.platform.aix.config.DataLoaderConfiguration;
import com.platform.aix.config.ServiceBeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 创建oracle表
 * @author Advance
 * @date 2022年01月17日 15:39
 * @since V1.0.0
 */
@Slf4j
public class CreateOracleTable implements CreateTable{

    @Override
    public void create(Connection conn, DatabaseMetaData metaData, DataLoaderConfiguration dlc) throws SQLException, IOException {
        log.info("===========开始创建oracle数据库表============");
        conn = ServiceBeanConfig.dataSource.getConnection();
        ResultSet rs = metaData.getTables(null,
                dlc.getUserName().toUpperCase(), "DR_SQL_LOADER_LOG", null); //参数schemaPattern: 数据库名称模式匹配，null表示不缩小搜索范围数据库名，对于oracle来说就用户名
        if (rs.next()) {
            log.info("DR_SQL_LOADER_LOG（sql执行日志表）表存在...不需要重新创建！");
        }  else{
            log.info("表不存在...使用【ScriptRunner】执行建表语句");
            ScriptRunner runner = new ScriptRunner(conn,false,true);
            Resources.setCharset(Charset.forName("UTF8"));
            runner.runScript(Resources.getResourceAsReader("sql/init.sql"));
            log.info("执行sql文件成功......");
        }
    }
}
