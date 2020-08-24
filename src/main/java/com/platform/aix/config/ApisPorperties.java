package com.platform.aix.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties(prefix = "platform.aix")
public class ApisPorperties {

    private boolean logToMongo = true;

    private boolean logResp = true;

    private int tokenExpire = 1800; // 秒

    private ApisServer server = new ApisServer();

    private String ecgFileParserUrl = "http://114.55.11.51:18081/";

    private String mailSubject = "房颤信息中心患者数据库";

    private String mailContent = "The sheet file download url:  <B>%s</B>.For security protection, Please download the sheet within 60 minutes.";

    private String mailSignature = "<br/>";

    // 资源文件公网下载地址的URL前缀
    private String downfilePublicPreurl = "http://192.168.9.116";

    // 保存资源文件的本地目录
//    private String saveFileLocalDir = "/mnt/zxfileserver/";
    private String saveFileLocalDir = "d://mnt//zxfileserver//";

    //  验证公卫平台帐号
    private boolean checkGwAccountFlag = true;          // 是否开启公卫帐号验证

    private String checkGwAccountUrl = "http://192.168.9.43:18066/aigw/api/v1/phis/cmd_01401001";

    private String appRegistFileFolder = "/var/www/html"; //app注册文件默认存放地址
    private String appRequestUrl = "https://192.168.9.201/"; //数据库 regsiterprotourl 前缀

}
