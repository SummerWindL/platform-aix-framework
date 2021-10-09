CREATE OR REPLACE FUNCTION  f_mb_role_group_function_delete(
			in_rolegrpid varchar(64),
			OUT retcode integer, 
			OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除角色组相关的功能列表。该函数只操作用户定义角色，不能操作系统决橘色。sysflag = 10000为系统角色功能，不能删除
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--	入参说明
	--	程序变量说明
	v_rec record;
begin

	delete from  t_mb_role_group_function 
	where (rolegrpid = in_rolegrpid and sysflag = 20000);

	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
