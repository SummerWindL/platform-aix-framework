CREATE OR REPLACE FUNCTION f_mb_prj_delete(
	in_projectid varchar(64), 
	OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：删除项目
 * in_projectid 指定的项目编码
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

	--先删除和该项目相关的东西
	-- todo:是否需要提示？
	--判断项目标题是否存在
	select count(1) into v_cnt from t_mb_prj_title where projectid = in_projectid;
	if (v_cnt != 0) then
		--删除项目标题
		PERFORM f_mb_prj_title_delete(in_projectid,'');
	end if;
	
	--删除项目
	delete from  t_mb_prj where projectid = in_projectid;
	
	retcode := 0;
  	retvalue := '{"error_msg":"success"}';

end

 $function$
