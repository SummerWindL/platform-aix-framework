CREATE OR REPLACE FUNCTION f_mb_user_account_doctorauthflag_update(
	in in_userid varchar(64),	--用户ID
	in in_acc varchar(64),	--账号
	in in_doctorauthflag int4,	-- 0：查看所有医生患者，1：仅查看责任医生患者
	
   OUT retcode integer, 
   OUT retvalue text
 )
LANGUAGE plpgsql
AS $function$
/*
* 函数说明：修改账号表医生权限标记
 * 参数：
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功 
 *     retvalue：返回关联表查询字段记录
*/

declare
	-- 入参说明
	-- 程序变量说明
	v_rec            record;
	v_modifiedtime  timestamp default now();	--修改时间

begin
	
	if (in_userid is null or trim(in_userid) = '') then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_doctorauthflag_update ->> in_userid value is null or empty"}';
		return;
	end if;

	if (in_acc is null or trim(in_acc) = '') then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_doctorauthflag_update ->> in_acc value is null or empty"}';
		return;
	end if;

	if (in_doctorauthflag = -1) then 
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_areaauthflag_update ->> in_doctorauthflag value is illegal"}';
		return;
	end if;

	update t_mb_user_account set 
		doctorauthflag = in_doctorauthflag,
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
