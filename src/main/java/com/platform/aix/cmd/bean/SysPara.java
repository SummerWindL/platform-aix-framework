package com.platform.aix.cmd.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 系统参数
 * @author Advance
 * @date 2022年05月29日 12:39
 * @since V1.0.0
 */
@Data
public class SysPara {
    private String systemcode;
    private String paracode;
    private String paravalue;
    private String datatype;
    private String datascope;
    private String remark;
    private String createuser;
    private String createdept;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    private String updateuser;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;
    private String institutioncode;
    private String updateflg;
}
