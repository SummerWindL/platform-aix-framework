CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_delete(
	in_userid varchar(64),
	in_hospcodelistjosn varchar(64),
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：批量删除账号关联的医院表。
 * 参数：in_hospcodelistjosn为空串''时，删除该userid下的所有医院
 *    in_hospcodelistjosn  格式为json数组，格式为：【"0001","0002","0003"】
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
	delete from  t_mb_user_auth_hosp 
	where (userid = in_userid) and  (in_hospcodelistjosn = '' or substring(in_hospcodelistjosn, hospcode) != '');

	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
