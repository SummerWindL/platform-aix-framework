package com.platform.aix.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Advance
 * @date 2021年10月13日 14:22
 * @since V1.0.0
 */
@Slf4j
public class FileUtil {
    public static void uploadFileByFTP1(FTPClient ftp, String realPath, String remotePath, String fileName) throws IOException {
        //设置ftp连接超时时间
        realPath = realPath + fileName;
        //ftp.changeWorkingDirectory(remotePath);
        createFtpDir(ftp,remotePath);
        remotePath = remotePath + fileName;
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        log.info("========文件上传路径:"+remotePath+"本地文件路径:"+realPath+"=======");
        FileInputStream fos = new FileInputStream(realPath);//需要上传的问价在本地磁盘的路径
        ftp.storeFile(remotePath, fos);
        fos.close();
    }
    public static boolean createFtpDir(FTPClient ftp,String dir){
        try {
            //目录编码，解决中文路径问题
            String str = new String(dir.toString().getBytes("GBK"),"iso-8859-1");
            if(ftp.changeWorkingDirectory(str)){
                //目录已存在
                return true;
            }
            dir = dir.substring(dir.indexOf("/")+1,dir.lastIndexOf("/"));
            String[] arr =  dir.split("/");
            StringBuffer sbfDir=new StringBuffer();
            //循环生成目录
            for(String s : arr){
                sbfDir.append("/").append(s);
                str = new String(sbfDir.toString().getBytes("GBK"),"iso-8859-1");
                //尝试切入目录
                if(ftp.changeWorkingDirectory(str)){
                    continue;
                }
                if(!ftp.makeDirectory(str)){
                    log.error("创建目录：["+sbfDir.toString()+"]失败！");
                    break;
                }
                log.error("创建目录：["+sbfDir.toString()+"]成功！");
            }
            //将目录切换至指定路径
            return ftp.changeWorkingDirectory(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
