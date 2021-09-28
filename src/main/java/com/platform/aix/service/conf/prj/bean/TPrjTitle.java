package com.platform.aix.service.conf.prj.bean;

import lombok.Data;

import java.util.Date;

/**
 * 项目标题
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Data
public class TPrjTitle {

    private String titleid;
    private String projectid;
    private String titlename;
    private String titlelogo;


    private Date modifiedtime;
    private Date createdtime;
}
