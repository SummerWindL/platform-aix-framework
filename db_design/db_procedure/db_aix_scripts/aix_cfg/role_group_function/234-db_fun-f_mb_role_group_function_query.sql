CREATE OR REPLACE FUNCTION f_mb_role_group_function_query(
		in_rolegrpid varchar(64),
		OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
 
/*
 * 函数说明：查询角色组的功能列表。
 * 参数：按要求填写参数，当所有参数为空串''时，查询所有记录，否则查询指定参数的记录
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回角色组的功能列表,有可能为空
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
AS $function$
declare

	--	程序变量说明
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
begin

	select array_to_json(array_agg(row_to_json(t))) into retvalue
	from (
		select a.rolegrpid,
				a.functionidlist,
				a.sysflag,
				a.createdtime,
				a.modifiedtime 
		from  t_mb_role_group_function a
    	where (in_rolegrpid = '' or a.rolegrpid = in_rolegrpid)
    	
	) AS t;
	--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;

end

 $function$
