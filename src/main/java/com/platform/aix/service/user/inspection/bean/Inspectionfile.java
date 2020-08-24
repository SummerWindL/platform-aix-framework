package com.platform.aix.service.user.inspection.bean;

import lombok.Data;

/**
 * @program: platform-aimb
 * @description: 检查文件JSON数据结构
 * @author: fuyl
 * @create: 2019-08-06 21:20
 **/
@Data
public class Inspectionfile {
    private String filelocationflag; //1- 本地存储 2- 文件服务器存储 3-mongo
    private String fileencode;  //文件编码格式 1-无编码 2-base64编码
    private String filetypecode; //参见文件类型编码表
    private String filetypename;
    private String filecontent;  //文件内容取决于文件类型编码和fileencode编码
    private String fileurl;
    private String fileid;
}
