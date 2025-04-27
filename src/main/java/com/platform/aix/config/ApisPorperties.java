package com.platform.aix.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@ToString
@Configuration
//@PropertySource(value = "classpath:aixconfig.properties",encoding = "utf-8")
@ConfigurationProperties(prefix = "platform.aix")
public class ApisPorperties {

    private boolean logToMongo = true;

    private boolean logResp = true;

    private int tokenExpire = 1800; // 秒

    private ApisServer server = new ApisServer();

    // 邮箱相关配置
    private Mail mail = new Mail();

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


    public class Mail{
        private String host = "";
        private Integer port;
        private String userName;
        private String passWord;
        private Integer expireHours;

        private String templateHost;
        private String templateToken;
        private String templateUUID;

        public String getTemplateHost() {
            return templateHost;
        }

        public void setTemplateHost(String templateHost) {
            this.templateHost = templateHost;
        }

        public String getTemplateToken() {
            return templateToken;
        }

        public void setTemplateToken(String templateToken) {
            this.templateToken = templateToken;
        }

        public String getTemplateUUID() {
            return templateUUID;
        }

        public void setTemplateUUID(String templateUUID) {
            this.templateUUID = templateUUID;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public Integer getExpireHours() {
            return expireHours;
        }

        public void setExpireHours(Integer expireHours) {
            this.expireHours = expireHours;
        }
    }

}
