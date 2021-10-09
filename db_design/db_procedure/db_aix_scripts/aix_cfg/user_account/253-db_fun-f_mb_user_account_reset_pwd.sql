CREATE OR REPLACE FUNCTION f_mb_user_account_reset_pwd(
   in in_userid               VARCHAR(64),
   in in_acc                  VARCHAR(64),
   in in_pwd                  VARCHAR(64),
   in in_salt                 VARCHAR(64),
   
   OUT retcode integer, 
   OUT retvalue text
)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：重置医生开通的账号密码
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--参数说明
	--程序变量
	v_modifiedtime  timestamp default now();
begin

	if (trim(in_userid) = '' or trim(in_acc) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"f_mb_user_account_reset_pwd ->> in_userid or in_acc value is null or empty"}';
		return;
	end if;
	
	update t_mb_user_account set
				   pwd = in_pwd,
				   salt = in_salt,
				   modifiedtime = v_modifiedtime
	where userid = in_userid AND acc = in_acc;
		
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
