CREATE OR REPLACE FUNCTION f_mb_role_group_insert(
	in_rolegrpid varchar(64),
	in_rolegrpname varchar(64),
	in_rolegrpicon text,
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：插入角色组。该函数只操作用户定义角色组。sysflag = 10000为系统角色
 * 参数：按要求填写参数
 *  * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--	入参说明
	--	程序变量说明
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
begin

	if (trim(in_rolegrpid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	--判断医院编码是否存在
  select count(1) into v_cnt from t_mb_role_group  
  where rolegrpid = in_rolegrpid or  rolegrpname = in_rolegrpname;
  if (v_cnt != 0) then
		retcode := -1;
		retvalue := '{"error_msg":"record already exist"}';
		return;
	end if;
	
	select now() into v_createdtime;
	v_modifiedtime := v_createdtime;

	insert into t_mb_role_group(
			 	rolegrpid,
				rolegrpname,
				sysflag,
				rolegrpicon,
			   createdtime,
			   modifiedtime)
	values (
			in_rolegrpid,
			in_rolegrpname,
			20000,
			in_rolegrpicon,
			v_createdtime,
			 v_modifiedtime);

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
