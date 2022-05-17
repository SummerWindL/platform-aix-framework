package com.platform.aix.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Advance
 * @date 2021年11月09日 10:19
 * @since V1.0.0
 */
@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties(prefix = "dataloader.main")
public class DataLoaderConfiguration {

    private boolean executeCreateTableFlag = false; //是否执行建表动作
    private String url;       //数据库驱动地址
    private String drive;     //数据库驱动
    private String userName;  //数据库用户
    private String passWord;  //数据库密码
    private String filePath;  //脚本路径

    private Generate generate; //二级配置

    private boolean runScript = false; //是否一启动就执行sql脚本 默认否
}
