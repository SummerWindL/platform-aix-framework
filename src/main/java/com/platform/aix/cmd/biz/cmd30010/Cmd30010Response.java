package com.platform.aix.cmd.biz.cmd30010;

import com.platform.aix.cmd.bean.response.MbHospDoctorRgngrp;
import com.platform.aix.cmd.bean.response.PrjInfo;
import com.platform.aix.cmd.bean.response.UserInfo;
import com.platform.aix.common.request.RequestAuthHeader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * @author: Alfred
 * @date: 2018/4/18
 * @description:
 */
@Setter
@Getter
@ToString
public class Cmd30010Response {

    private String userid;

    // token过期时间
    private int tokenexpire;

    private RequestAuthHeader auth;

    private UserInfo userinfo;

    //医生所属区划
    private List<MbHospDoctorRgngrp> mbHospDoctorRgngrps;

    private PrjInfo prjinfo;

    private JSONArray functionMenu;
}
