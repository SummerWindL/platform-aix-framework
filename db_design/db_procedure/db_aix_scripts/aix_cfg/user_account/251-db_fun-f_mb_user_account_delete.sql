CREATE OR REPLACE FUNCTION f_mb_user_account_delete(
	in in_userid				varchar(64),
	in in_acc                  varchar(64),
	
	OUT retcode integer,
	OUT retvalue text
)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除医生开通的账号
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
	v_cnt int4;
	v_rec record;
begin
	
	if(in_acc = 'admin') then	--不能删除管理员账号
		retcode := -11;
		retvalue := '{"error_msg":"f_mb_user_account_delete ->> admin acc is not allowed delete"}';
		return;
	end if;
	
	if (in_userid is null or trim(in_userid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_delete ->> in_userid value is null or empty"}';
		return;
	end if;
	
	if (in_acc is null or trim(in_acc) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_delete ->> in_acc value is null or empty"}';
		return;
	end if;

-----------------------关闭账号， 医生开通的账号需要删除的东西 ---------------------------------
	--	删除该用户所关联的机构权限
	delete from t_mb_user_auth_hosp where userid = in_userid;

	--	删除该用户所关联的科室权限
	delete from t_mb_user_auth_hosp_dept where userid = in_userid;

	-- 删除用户所关联的划区表
	delete from t_mb_hosp_doctor_area where doctoruserid = in_userid;

	--	删除该用户所能访问的项目数据
	delete from t_mb_user_account_prj_tiltle where userid = in_userid;

	--删除账号组关联表
	PERFORM f_mb_role_group_account_delete(in_userid,'');

	--置空t_mb_user_info的acc字段
	update t_mb_user_info set acc = '' where userid = in_userid;	

	--删除账号
	delete from  t_mb_user_account where acc = in_acc and  userid = in_userid;
	
	retcode := 0;
  	retvalue := '{"error_msg":"success"}';
  
end

 $function$
