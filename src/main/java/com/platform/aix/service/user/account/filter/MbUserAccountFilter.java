package com.platform.aix.service.user.account.filter;

import com.platform.aix.cmd.bean.filter.BasePageFilter;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 *
 *
 * @author:
 * @date: Thu Apr 09 11:03:20 CST 2020
 * @description: 帐号不允许修改，只能逻辑删除
 */
@Data
public class MbUserAccountFilter extends BasePageFilter {

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

	private String regtime;		//	注册时间

	public String getAcc() {
		return StringUtils.isEmpty(acc)?"":acc;
	}
	public String getPwd() {
		return StringUtils.isEmpty(pwd)?"":pwd;
	}
	public String getSalt() {
		return StringUtils.isEmpty(salt)?"":salt;
	}
	public String getEnableflag() {
		return StringUtils.isEmpty(enableflag)?"":enableflag;
	}
	public Integer getSysflag() {
		return StringUtils.isEmpty(sysflag)?-1:sysflag;
	}
	public Integer getHospauthflag() {
		return StringUtils.isEmpty(hospauthflag)?-1:hospauthflag;
	}
	public Integer getDeptauthflag() {
		return StringUtils.isEmpty(deptauthflag)?-1:deptauthflag;
	}
	public Integer getDoctorauthflag() {
		return StringUtils.isEmpty(doctorauthflag)?-1:doctorauthflag;
	}
	public Integer getAreaauthflag() {
		return StringUtils.isEmpty(areaauthflag)?-1:areaauthflag;
	}
	public Integer getReserveauthflag() {
		return StringUtils.isEmpty(reserveauthflag)?-1:reserveauthflag;
	}
	public String getRegtime() {
		return StringUtils.isEmpty(regtime)?"":regtime;
	}
}