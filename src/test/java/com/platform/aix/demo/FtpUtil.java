package com.platform.aix.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Advance
 * @date 2021年10月13日 14:10
 * @since V1.0.0
 */
@Slf4j
public class FtpUtil {
    private static FTPClient ftp = new FTPClient();
    public static final String ip = "127.0.0.1";
    public static final String username = "ftpuser";
    public static final String password = "Admin1234";
    public static final int port = 21;

    public static FTPClient getFtpClient() throws Exception {
        return ftp;
    }
    /**
     * 连接FTP
     * @param ftpIp ftp服务器ip
     * @param ftpPort ftp服务器端口号
     * @param username 账户
     * @param password 密码
     * @throws Exception
     */
    public static void FTPConnect(String ftpIp,int ftpPort,String username,String password) throws Exception {
        ftp.connect(ftpIp,ftpPort);
        boolean isLogin = ftp.login(username, password);
        if (!isLogin) {
            log.error("登录ftp服务失败！");
            throw new Exception("登录ftp服务失败！");
        }else{
            log.info("登录ftp服务成功！");
        }
    }
    /**
     * 关闭ftp
     * @throws IOException
     */
    public static  void FTPDisConnect() throws IOException {
        if (ftp!=null&&ftp.isConnected()) {
            ftp.disconnect();
        }
    }

    public static void main(String[] args) throws Exception {
        FTPConnect(ip,port,username,password);
        //getFtpClient(ip,port,username,password);
    }
}
