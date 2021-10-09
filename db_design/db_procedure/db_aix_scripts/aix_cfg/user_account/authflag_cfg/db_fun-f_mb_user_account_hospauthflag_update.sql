CREATE OR REPLACE FUNCTION f_mb_user_account_hospauthflag_update(
	in in_userid varchar(64),	--用户ID
	in in_acc varchar(64),	--账号
	in in_hospauthflag int4,	-- 0：查看所有医院，1：查看指定机构
	
   OUT retcode integer, 
   OUT retvalue text
 )
LANGUAGE plpgsql
AS $function$
/*
* 函数说明：修改账号表医院权限标记
 * 参数：
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功 
 *     retvalue：返回关联表查询字段记录
 * 
 * 
*/

declare
	-- 入参说明
	-- 程序变量说明
	v_rec            record;
	v_modifiedtime  timestamp default now();	--修改时间

begin
	
	if (in_userid is null or trim(in_userid) = '') then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_hospauthflag_update ->> in_userid value is null or empty"}';
		return;
	end if;

	if (in_acc is null or trim(in_acc) = '') then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_hospauthflag_update ->> in_acc value is null or empty"}';
		return;
	end if;

	if (in_hospauthflag = -1) then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_areaauthflag_update ->> in_hospauthflag value is illegal"}';
		return;
	end if;

	update t_mb_user_account set 
		hospauthflag = in_hospauthflag,
		modifiedtime = v_modifiedtime
	where 
		userid = in_userid and
		acc = in_acc
	;

	-- 处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
