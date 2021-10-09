CREATE OR REPLACE FUNCTION f_mb_prj_title_update (
	in_projectid varchar(64),
	in_titleid varchar(64),
	in_titlename varchar(255),
	in_titlelogo text,
	
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：更新项目标题。
 * 参数：按要求填写参数
 *  * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--	入参说明
	--	程序变量说明
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
begin

	if (trim(in_projectid) = '' or trim(in_titleid) = '' or trim(in_titlename) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;
	
	--更新
	select now() into v_modifiedtime;
	update t_mb_prj_title set
			titlename = in_titlename,
			titlelogo = in_titlelogo,
			modifiedtime = v_modifiedtime
		where projectid = in_projectid 
		and titleid = in_titleid; 

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
 