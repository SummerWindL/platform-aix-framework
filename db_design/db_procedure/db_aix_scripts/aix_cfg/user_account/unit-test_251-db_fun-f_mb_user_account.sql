-----------------------------------------
-- 单元测试
-- 医生开通的账号
-----------------------------------------

-- 开通医生账号
select *  from f_mb_user_account_insert(
 	'45b704aa308642bda6cbe3f80b47a2d0',	--		in in_userid	 varchar (64),	--用户ID
 	'00008a45ea254cd0b9a2b60141aeea84',	--		in in_hospcode varchar(64),	--医院ID
	'dbx',	--		in in_acc varchar (64),	--帐号acc ID
	'dbxms',	--		in in_pwd varchar (64),	--密码
	'lo',	--		in in_salt varchar (64),	--加密因子
	20000	--		in in_sysflag int4,	--系统标记
);

-- 重置医生开通的账号密码
select *  from f_mb_user_account_reset_pwd(
	'45b704aa308642bda6cbe3f80b47a2d0',	--	   in in_userid               VARCHAR(64),
	'dbx',	--	   in in_acc                  VARCHAR(64),
	'dbxmsms',	--	   in in_pwd                  VARCHAR(64),
	'lo'	--	   in in_salt                 VARCHAR(64),
);

-- 关闭医生开通的账号
select *  from f_mb_user_account_delete(
	'45b704aa308642bda6cbe3f80b47a2d0',	--in in_userid				varchar(64),	医生ID
	'dbx'	--in in_acc                  varchar(64),   医生开通的账号ID
);

















