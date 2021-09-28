package com.platform.aix.cmd.biz.baseconf.cmd41230;

import com.platform.aix.cmd.bean.request.BaseRequestPage;
import lombok.Data;

/**
 * 查询项目标题
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41230Req extends BaseRequestPage {

    private String projectid = "";
    private String titleid = "";
    private String titlename = "";

//    public String getProjectid() {
//        return "%" + projectid + "%";
//    }
//
//    public String getTitleid() {
//        return "%" + titleid + "%";
//    }
//
//    public String getTitlename() {
//        return "%" + titlename + "%";
//    }
}