CREATE OR REPLACE FUNCTION f_mb_user_account_insert(
 	in in_userid	 varchar (64),	--用户ID
 	in in_hospcode varchar(64),	--医院ID
	in in_acc varchar (64),	--帐号acc ID
	in in_pwd varchar (64),	--密码
	in in_salt varchar (64),	--加密因子
	in in_sysflag int4,	--系统标记

	OUT retcode integer,
	OUT retvalue text
)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：开通医生账号
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--	入参说明
	--	程序变量说明
    v_cnt	int4 DEFAULT 0;
	v_createdtime	timestamp default now();
	v_modifiedtime	timestamp default now();
	
	v_deptcode varchar(64) default '';
	v_mbHospDoctorRgn  jsonb default '[]' ;		--	为新帐号默认初始化的 虚拟未知划区村组	
begin
	
	-- 所有通过界面新增的账户都是普通账户
	in_sysflag := 20000;
	
	if (trim(in_userid) = '' or trim(in_acc) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_insert ->> in_userid or in_acc value is null or empty"}';
		return;
	end if;

	if (in_hospcode is null or trim(in_hospcode) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_insert ->> in_hospcode value is null or empty"}';
		return;
	end if;

	if (in_pwd is null or trim(in_pwd) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_insert ->> in_pwd value is null or empty"}';
		return;
	end if;
	
	if (in_salt is null or trim(in_salt) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_insert ->> in_salt value is null or empty"}';
		return;
	end if;

	if (in_sysflag is null) then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_insert ->> in_sysflag value is null"}';
		return;
	end if;

	select count(1) into v_cnt from t_mb_user_account a where a.acc = in_acc;
	if (v_cnt > 0) then
			retcode := -1;
			retvalue := '{"error_msg":"f_mb_user_account_insert ->> record already exist"}';
			return;
	end if;

	insert into t_mb_user_account(
		userid,	--用户ID
		acc,	--帐号
		pwd,	--密码
		salt,	--加密因子
		enableflag,	--使能标记
		sysflag,	--系统标记
		hospauthflag,	--医院权限标记
		deptauthflag,	--科室权限标记
		doctorauthflag,	--医生权限标记
		areaauthflag,	--划区权限标记
		reserveauthflag,	--保留标记
		regtime,	--注册时间
		modifiedtime,	--修改时间
		createdtime	--创建时间
	 )
	values (
	 	in_userid,	--用户ID
		in_acc,	--帐号acc ID
		in_pwd,	--密码
		in_salt,	--加密因子
		'10000',	--使能标记
		in_sysflag,	--系统标记
		1,	--医院权限标记
		1,	--科室权限标记
		0,	--医生权限标记
		1,	--划区权限标记
		0,	--保留标记
		v_createdtime,	--注册时间
		v_modifiedtime,	--修改时间
		v_createdtime	--创建时间
	);
	 
	--将用户所属医院默认配置到权限医院
	perform f_mb_user_auth_hosp_insert(
	             in_userid ,
	             in_hospcode
	);			 

	--将doctor acc 更新到 t_mb_user_info 的 acc 字段
	update t_mb_user_info set acc = in_acc where hospcode = in_hospcode and userid = in_userid;

	--将添加医院医生时配置的科室，配置到科室权限表
	select deptcode into v_deptcode from t_mb_user_info where userid = in_userid;
	insert into t_mb_user_auth_hosp_dept (
	 	userid,
		hospcode,
		deptcode,
		createdtime, 
		modifiedtime 
	) values (
		in_userid,
 		in_hospcode,
 		v_deptcode,
 		v_createdtime,
 		v_modifiedtime
	);

	--	为 帐号 配置 虚拟未知划区 权限 ，构造 默认参数
		select jsonb_insert(	v_mbHospDoctorRgn, 
										'{0}', 
										jsonb_build_object ( 	'hospcode' , 	in_hospcode ,
																		'doctoruserid' , 	in_userid ,
																		'doctorusername' , 	'',
																		'regionlevel' , 	6,		--	TODO 这里硬编码？
																		'areacode' , 	'000000',
																		'areaname' , 	'未知划区',
																		'areafullname' , 	'未知划区',
																		'arealevel' , 	0,
																		'parentareacode' , 	'999999'
										 ) ,
										false ) 
		into v_mbHospDoctorRgn ;

		perform f_mb_hosp_doctor_area_insert(in_userid, v_mbHospDoctorRgn) ;
				 
	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
