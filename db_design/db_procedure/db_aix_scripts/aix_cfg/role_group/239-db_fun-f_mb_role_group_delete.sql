CREATE OR REPLACE FUNCTION f_mb_role_group_delete(
	in_rolegrpid varchar(64),
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除角色组。sysflag = 20000为系统角色，系统角色不能删除
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
	v_rec record;
begin
	--先删除和该角色相关的东西
	--判断医院是否存在
	select count(1) into v_cnt from t_mb_role_group where rolegrpid = in_rolegrpid and sysflag = 20000;
	if (v_cnt != 0) then
		--删除组功能
		PERFORM f_mb_role_group_function_delete(in_rolegrpid);
		--删除组关联的账号
		delete from t_mb_role_group_account where rolegrpid = in_rolegrpid;
	end if;
	
	--删除组
	delete from  t_mb_role_group where (rolegrpid = in_rolegrpid and sysflag = 20000 );

	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
