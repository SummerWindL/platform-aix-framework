package com.platform.aix.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Advance
 * @date 2022年05月29日 14:54
 * @since V1.0.0
 */
@Slf4j
public class FileUtil {

    //存放文件的 文件夹
    static String FileMkdir="ExcelFile";

    static String filePath = FileUtil.class.getResource("/").getPath();

    public static void uploadFileByFTP1(FTPClient ftp, String realPath, String remotePath, String fileName) throws IOException {
        //设置ftp连接超时时间
        realPath = realPath + fileName;
        //ftp.changeWorkingDirectory(remotePath);
        createFtpDir(ftp,remotePath);
        remotePath = remotePath + fileName;
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        log.info("========文件上传路径:"+remotePath+"本地文件路径:"+realPath+"=======");
        FileInputStream fos = new FileInputStream(realPath);//需要上传的文件在本地磁盘的路径
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

    public static String upload(MultipartFile head_pic) throws IOException {
        String finalFileName = "";
        if (head_pic.getSize() > 0) {
            String FileName = head_pic.getOriginalFilename();
            finalFileName = new File(filePath).getParentFile().getParentFile().getAbsolutePath()+"\\"+FileMkdir+"\\"+FileName;
            System.out.println(finalFileName);
            //目标文件
            File file = new File(finalFileName);
            if (!file.exists()) {
                //开始上传
                head_pic.transferTo(file);
            }else{
                return null;
            }
        }
        return finalFileName;
    }

    /**
     * 以文件名字里有的字符进行匹配删除
     * @param fileName
     */
    public static void deleteExcelFile(String fileName){
        File file = new File(new File(filePath).getParentFile().getParentFile().getAbsolutePath()+"\\"+FileMkdir);
        File[] list = file.listFiles();
        //如果等于空就删除全部文件
        if(fileName!=null){
            for (File f:list) {
                if(f.getName().contains(fileName)){
                    f.delete();
                    return;
                }
            }
        }
        for (File f:list) {
            f.delete();
        }

    }

}

