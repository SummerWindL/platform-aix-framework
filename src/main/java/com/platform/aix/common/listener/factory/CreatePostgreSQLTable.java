package com.platform.aix.common.listener.factory;

import com.platform.aix.common.loader.ScriptRunner;
import com.platform.aix.config.DataLoaderConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static com.platform.aix.config.ServiceBeanConfig.jdbcTemplate;

/**
 * 创建postgreSQL表
 * @author Advance
 * @date 2022年01月17日 15:39
 * @since V1.0.0
 */
@Slf4j
public class CreatePostgreSQLTable implements CreateTable{

    @Override
    public void create(Connection conn, DatabaseMetaData metaData, DataLoaderConfiguration dlc) throws SQLException, IOException {
        log.info("===========开始创建postgreSQL数据库表============");
        Integer count = jdbcTemplate.queryForObject("select count(*) from pg_class where relname = 'dr_sql_loader_log'", Integer.class);
        if (count>0) {
            log.info("DR_SQL_LOADER_LOG（sql执行日志表）表存在...不需要重新创建！");
        }  else{
            log.info("表不存在...使用【ScriptRunner】执行建表语句");
            ScriptRunner runner = new ScriptRunner(conn,false,true);
            Resources.setCharset(Charset.forName("UTF8"));
            runner.runScript(Resources.getResourceAsReader("sql/init_postgre.sql"));
            log.info("执行sql文件成功......");
        }
    }
}
