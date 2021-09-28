package com.platform.aix.service.user.account.bean;

import lombok.Data;

import java.util.Date;


/**
 *
 *
 * @author:
 * @date: Thu Apr 09 11:03:20 CST 2020
 * @description: 帐号不允许修改，只能逻辑删除
 */
@Data
public class MbUserAccountBean {

	private String userid;		//	用户ID

	private String acc;		//	帐号

	private String pwd;		//	密码

	private String salt;		//	加密因子

	private String enableflag;		//	1=正常 2=禁用

	private Integer sysflag;		//	10000=系统管理员 20000=用户

	private Integer hospauthflag;		//	0,查看所有医院， 1查看指定机构

	private Integer deptauthflag;		//	0,查看所有科室， 1查看指定科室

	private Integer doctorauthflag;		//	0,查看所有医生患者 1仅查看责任医生患者

	private Integer areaauthflag;		//	0 查看所有划区，1查看指定划区

	private Integer reserveauthflag;		//	保留标记

	private Date regtime;		//	注册时间

	private Date modifiedtime;		//	修改时间

	private Date createdtime;		//	创建时间
}