CREATE OR REPLACE FUNCTION f_mb_prj_title_delete(
	in_projectid varchar(64), 
	in_titleid varchar(64), 
	
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除项目标题
 * 参数：in_hospcode 指定的医院编码
 * 返回：
 *     retcode： 小于0，失败;== 0 成功
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

	--先删除和该项目标题相关的东西
	--判断用户项目标题是否存在
	select count(1) into v_cnt from t_mb_user_account_prj_tiltle where projectid = in_projectid;
	if (v_cnt != 0) then
		--删除用户项目标题
		delete from  t_mb_user_account_prj_tiltle 
			where (projectid = in_projectid) and  (in_titleid  = '' OR in_titleid = titleid);
	end if;

	--删除项目标题
	delete from  t_mb_prj_title 
	where (projectid = in_projectid) and  (in_titleid  = '' OR in_titleid = titleid);
	
	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
