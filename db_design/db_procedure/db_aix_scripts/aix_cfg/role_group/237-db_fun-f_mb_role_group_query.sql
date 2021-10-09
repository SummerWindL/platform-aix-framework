CREATE OR REPLACE FUNCTION f_mb_role_group_query(
	in in_rolegrpid varchar(64),
	in in_countflag integer, 
	in in_offset integer, 
	in in_pagesize integer,
	OUT retcode integer, 
	OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：查询角色组。
 * 参数：按要求填写参数，当所有参数为空串''时，查询所有记录，否则查询指定参数的记录
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回角色组的功能列表,有可能为空
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
	v_rec            record;
begin

	
	with c_mb_record as (
			select a.rolegrpid, a.rolegrpname, a.rolegrpicon,
			(select string_agg(c.functionname||'',', ') from t_mb_function_menu c 
			where c.functionid in (select * from json_array_elements_text(b.functionidlist::json)) and c.functionid!='000000') as functionlist
			from  t_mb_role_group a
			LEFT JOIN t_mb_role_group_function b ON a.rolegrpid = b.rolegrpid
    		where (in_rolegrpid = '' or a.rolegrpid = in_rolegrpid)
			order by a.rolegrpid
		),
	--	虚拟表前缀 o_l_c 代表对前定义的虚拟表，增加了记录数限定条件
	c_mb_record_limit as
	(
		select * from c_mb_record  offset in_offset limit in_pagesize
	)		

	select 
		case when (in_countflag = 2)  then 
				(select count(1) from c_mb_record ) end as ret_code, 
		case when (in_countflag = 3) then 
				(select array_to_json(array_agg(row_to_json(t))) from c_mb_record_limit t ) end as ret_value
	into v_rec;
			
	retcode := v_rec.ret_code;
	retvalue := v_rec.ret_value;
	
	if (retcode is NULL) THEN
		retcode := 0; --成功
	end if;

	if (retvalue is NULL) then 
		retvalue := '[]';
	end if;
end

 $function$
