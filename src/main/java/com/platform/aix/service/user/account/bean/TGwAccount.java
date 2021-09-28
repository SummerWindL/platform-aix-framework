package com.platform.aix.service.user.account.bean;

import lombok.Data;

import java.util.Date;

/**
 * @program: platform-aimb
 * @description: 公卫账号
 * @author: fuyl
 * @create: 2019-08-22 16:31
 **/
@Data
public class TGwAccount {
    /**
     * id
     */
    private String id;

    /**
     * userid
     */
    private String userid;

    /**
     * hospcode
     */
    private String hospcode;

    /**
     * gwuserid
     */
    private String gwuserid;

    /**
     * gwusername
     */
    private String gwusername;

    /**
     * gwpassword
     */
    private String gwpassword;

    /**
     * gwdoctorname
     */
    private String gwdoctorname;

    /**
     * gworgid
     */
    private String gworgid;

    /**
     * gworgname
     */
    private String gworgname;

    /**
     * gwhospadminflag
     */
    private Integer gwhospadminflag;

    /**
     * gwuserloginflag
     */
    private Integer gwuserloginflag;

    /**
     * gwuserlogintime
     */
    private Date gwuserlogintime;

    /**
     * gwapiloginflag
     */
    private Integer gwapiloginflag;

    /**
     * gwapilogintime
     */
    private Date gwapilogintime;

    /**
     * modifiedtime
     */
    private Date modifiedtime;

    /**
     * createdtime
     */
    private Date createdtime;
}
