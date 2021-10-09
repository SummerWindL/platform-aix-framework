CREATE OR REPLACE FUNCTION f_mb_role_group_function_save(
	in_rolegrpid varchar(64),
	in_functionidlistjosn TEXT,
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：保存角色组的功能列表。该函数只操作用户定义角色组的功能列表。sysflag = 10000为系统角色
 * 参数：按要求填写参数
 * --in_functionidlist 格式为JOSN数组格式，如["222","222","222"]
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/

declare

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
  	select count(1) into v_cnt from t_mb_role_group_function  where rolegrpid = in_rolegrpid;
  
	if (v_cnt = 0) then --记录不存在，插入记录
		
		select now() into v_createdtime;
		v_modifiedtime := v_createdtime;
		insert into t_mb_role_group_function(
					rolegrpid,
					functionidlist,
					sysflag,
					createdtime,
					modifiedtime)
	 	values (
					in_rolegrpid,
					in_functionidlistjosn,
				 	20000,
				   v_createdtime,
				   v_modifiedtime);
	ELSE --记录已经存在，只需要修改
		--修改医院信息
		select now() into v_modifiedtime;
	  	update t_mb_role_group_function set
			functionidlist = in_functionidlistjosn,
		    modifiedtime =  v_modifiedtime
		where (rolegrpid = in_rolegrpid and sysflag = 20000); 		
	
	end if;

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
