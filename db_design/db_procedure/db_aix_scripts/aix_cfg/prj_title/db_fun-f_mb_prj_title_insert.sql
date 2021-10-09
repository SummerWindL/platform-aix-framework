CREATE OR REPLACE FUNCTION f_mb_prj_title_insert (
	in_projectid VARCHAR(64),
	in_titleid VARCHAR(64),
	in_titlename VARCHAR(255),
	in_titlelogo text,
	
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：插入项目标题。
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
	
	v_cnt  int4  default 0 ; 	
	
begin
	
	if (in_projectid is null or trim(in_projectid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"in_projectid value is null or empty"}';
		return;
	end if;
	
	if (in_titlename is null or trim(in_titlename) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"in_titlename value is null or empty"}';
		return;
	end if;
	
	
	if (in_titleid is null or trim(in_titleid) = '') then
		select zxuuid() into in_titleid;  
	end if;
	
	--判定项目是否存在重名的标题
	select count(1) into v_cnt from t_mb_prj_title where projectid = in_projectid and titlename = in_titlename;
	if (v_cnt != 0) then
		retcode := -1;
		retvalue := '{"error_msg":"record already exist"}';
		return;
	end if;
		
	insert into t_mb_prj_title (
			 	projectid,
				titleid,
				titlename,
				titlelogo,
			   createdtime,
			   modifiedtime)
	values (
			in_projectid,
			in_titleid,
			in_titlename,
			in_titlelogo,
			v_createdtime,
			 v_modifiedtime);

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
