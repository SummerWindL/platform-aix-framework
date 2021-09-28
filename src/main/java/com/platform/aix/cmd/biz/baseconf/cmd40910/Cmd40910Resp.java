package com.platform.aix.cmd.biz.baseconf.cmd40910;

import lombok.Data;

import java.util.Date;

/**
 * 角色组功能关联
 *
 * @author: fyw
 * @date: 2018/8/1
 * @description:
 */
@Data
public class Cmd40910Resp {

    private String rolegrpid;
    private String functionidlist;
    private Integer sysflag;
    private Date createdtime;
    private Date modifiedtime;
}
