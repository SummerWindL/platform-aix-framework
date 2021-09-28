package com.platform.aix.service.conf.prj.bean;

import lombok.Data;

import java.util.Date;

/**
 * 项目
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Data
public class TPrj {

    private String projectid;
    private String projectname;
    private String projectdesc;

    private Date modifiedtime;
    private Date createdtime;
}
