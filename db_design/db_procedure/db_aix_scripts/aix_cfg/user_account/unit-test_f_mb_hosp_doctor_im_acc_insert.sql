----------------------------------------------------------
-- 单元测试
-- 添加医生IM账号
----------------------------------------------------------
delete from t_nethosp_doctor_im_acc where doctorid = '123456';

select * from f_mb_hosp_doctor_im_acc_insert(
	'aaa',	-- in_hospcode varchar(64),  -- 医院编码
	'sss',	-- in_deptcode varchar(64),  -- 科室编码
	'123456',	-- in_doctorid varchar(64),  -- 医生ID
	'1',	-- in_imtype varchar(64),  -- 1.网易IM 2.网易音视频
	'123456',	-- in_imacc varchar(64),  -- IM账号
	'ssssiiiogggggnnnn',	-- in_imusersign varchar(256),  -- IM用户签名
	'[]',	-- in_imoauthinfo text,  -- json格式字符串，第三方授权后返回的第三方帐号信息，不同的第三方系统字段不同
	1,	-- in_registstateflag int4,  -- 0未注册，1已注册
	0,	-- in_lastloginflag int4,  -- 0未登录 1已登录
	'',	-- in_lastlogintokenid varchar(256),  -- 最后登录tokenID
	'',	-- in_laslogintime varchar(64),  -- 最后登录时间
	1,	-- in_activestateflag int4,  -- 0不活动， 1活动
	0,	-- in_onlineflag int4,  -- 0离线， 1在线
	1	-- in_enableflag int4,  -- 0,无效，1有效
);
