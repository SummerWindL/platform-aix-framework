package com.platform.aix.common.listener;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.platform.aix.config.DataLoaderConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * screw生成表结构说明
 * @author Advance
 * @date 2021年11月09日 17:41
 * @since V1.0.0
 */
@Slf4j
@Order(value = 99)
@Component
public class TableStructureDescription extends StartupConfiguration {
    private static String fileOutputDir = "E:\\";

    @Autowired
    private DataLoaderConfiguration configuration;

    /**
     * 文档生成
     */
    void documentGeneration() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(configuration.getDrive());
        hikariConfig.setJdbcUrl(configuration.getUrl());
        hikariConfig.setUsername(configuration.getUserName());
        hikariConfig.setPassword(configuration.getPassWord());
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);


        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(configuration.getGenerate().getFileOutputDir())
                //打开目录
                .openOutputDir(true)
                //文件类型
                .fileType(configuration.getGenerate().getEngineFileType())
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName(configuration.getGenerate().getFileName()).build();

        //忽略表
        ArrayList<String> ignoreTableName = new ArrayList<>();
        ignoreTableName.add("test_user");
        ignoreTableName.add("test_group");
        //忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("test_");
        ignorePrefix.add("CHB_"); //报送
        ignorePrefix.add("REP_");
        //忽略表后缀
        ArrayList<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");
        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration config = Configuration.builder()
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
        //执行生成
        new DocumentationExecute(config).execute();
    }

    @Override
    public void afterStartup(ApplicationContext applicationContext) {
        log.info("服务启动成功，开始启动数据库表结构生成监听...");
        if(configuration.getGenerate().isImGenTableDoc()){
            log.info("==========开始生成数据库表结构===========");
            documentGeneration();
            log.info("==========结束生成数据库表结构===========");
        }
    }
}
