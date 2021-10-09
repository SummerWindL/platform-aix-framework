CREATE OR REPLACE FUNCTION f_mb_role_group_account_delete(
	in_userid varchar(64),
	in_rolegrpid varchar(64),
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除角色账号关联关系。
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
	v_rec record;
begin
	--删除组
	delete from  t_mb_role_group_account 
	where (userid = in_userid) and  (in_rolegrpid = '' or rolegrpid = in_rolegrpid);

	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
