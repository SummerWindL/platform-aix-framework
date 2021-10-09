CREATE OR REPLACE FUNCTION f_mb_prj_insert (
	in_projectid VARCHAR(64),
	in_projectname VARCHAR(255),
	in_projectdesc text,
	in_hospcode VARCHAR(64),
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：插入项目。
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

	
	if (in_projectname is null or trim(in_projectname) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"in_projectname value is null or empty"}';
		return;
	end if;
	
	if (in_hospcode is null or trim(in_hospcode) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"in_hospcode value is null or empty"}';
		return;
	end if;
	
	
	if (in_projectid is null or trim(in_projectid) = '') then
		select zxuuid() into in_projectid;  
	end if;
	
	--判定医院是否存在重名的项目名称
	select count(1) into v_cnt from t_mb_prj where hospcode = in_hospcode and projectname = in_projectname;
	if (v_cnt != 0) then
		retcode := -1;
		retvalue := '{"error_msg":"record already exist"}';
		return;
	end if;
	
	insert into t_mb_prj (
			 	projectid,
				projectname,
				projectdesc,
				hospcode,
			  createdtime,
			  modifiedtime
		) values (
			in_projectid,
			in_projectname,
			in_projectdesc,
			in_hospcode,
			v_createdtime,
			 v_modifiedtime);

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
