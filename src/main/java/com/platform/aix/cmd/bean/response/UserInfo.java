package com.platform.aix.cmd.bean.response;

import com.platform.aix.service.user.account.bean.AuthHosp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author: Alfred
 * @date: 2018/4/18
 * @description:
 */
@Setter
@Getter
@ToString
public class UserInfo {

    private String username;

    private Date regtime;

    private String workno;

    private String hospcode;

    private Integer hospinternetflag;   //互联网医院开通标记：0-未开通，1-开通

    /**
     * 登录实体新增 权限医院，新增医院APP账号相关 2019年3月14日15:41:42 fuyanliang
     */
    private List<AuthHosp> authospcode;

    private String appdoctoraccount;

    private String appdoctorname;

    private String appdoctorid;

    private String hospname;

    private String deptcode;

    private String deptname;

}
