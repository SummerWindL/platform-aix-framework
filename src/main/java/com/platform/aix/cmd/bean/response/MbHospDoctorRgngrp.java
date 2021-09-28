package com.platform.aix.cmd.bean.response;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: platform-aimb
 * @description: 医生划区表
 * @author: fuyl
 * @create: 2019-07-03 19:18
 **/
@Data
public class MbHospDoctorRgngrp {
    /**
     * hospcode
     */
    @NotNull(message = "医院编码不为空")
    private String hospcode;

    /**
     * doctoruserid
     */
    @NotNull(message = "医生id不能空")
    private String doctoruserid;

    /**
     * doctorusername
     */
    private String doctorusername;

    /**
     * regionlevel
     */
    private Integer regionlevel;

    /**
     * regioncode
     */
    @NotNull(message = "划区代码不能空")
    private String regioncode;

    /**
     * regionname
     */
    private String regionname;

    /**
     * regionfullname
     */
    private String regionfullname;

    /**
     * groupcode
     */
    @NotNull(message = "分组代码不能空")
    private String groupcode;

    /**
     * groupname
     */
    private String groupname;

    /**
     * regiongroupcode
     */
    private String regiongroupcode;

    /**
     * regiongroupname
     */
    private String regiongroupname;

    /**
     * regiongroupfullname
     */
    private String regiongroupfullname;

    /**
     * regiongroupmemo
     */
    private String regiongroupmemo;

    /**
     * createdtime
     */
    private Date createdtime;

    /**
     * modifiedtime
     */
    private Date modifiedtime;

}
