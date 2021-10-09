CREATE OR REPLACE FUNCTION f_mb_function_menu_delete(
	in_functionid varchar(64), 
	in_lang  	VARCHAR(64),
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：--删除网关。
 * 参数：in_hospcode 指定的医院编码
 *     in_gwid 网关ID
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
	v_rec record;
begin
	
	delete from  t_mb_function_menu where (functionid = in_functionid and lang = in_lang);

	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$