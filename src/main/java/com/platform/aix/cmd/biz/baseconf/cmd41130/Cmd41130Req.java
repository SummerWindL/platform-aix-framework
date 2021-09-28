package com.platform.aix.cmd.biz.baseconf.cmd41130;

import com.platform.aix.cmd.bean.request.BaseRequestPage;
import lombok.Data;

/**
 * 查询项目
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41130Req extends BaseRequestPage {

    private String projectid = "";
    private String projectname = "";
    //根据医院查询项目，查询指定医院下项目列表
    private String hospcode = "";

//    public String getProjectid() {
//        return "%" + projectid + "%";
//    }
//
//    public String getProjectname() {
//        return "%" + projectname + "%";
//    }
}