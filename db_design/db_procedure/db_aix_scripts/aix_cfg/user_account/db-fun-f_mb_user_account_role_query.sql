CREATE OR REPLACE FUNCTION "f_mb_user_account_role_query"(IN "in_userid" varchar,IN "in_roleid" varchar,IN "in_rolename" varchar, OUT "retcode" int4, OUT "retvalue" text)
  RETURNS "pg_catalog"."record" AS $BODY$
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
begin

	select array_to_json(array_agg(row_to_json(t))) into retvalue
	from (
			select a.rolegrpid, a.rolegrpname, 
				(select string_agg(c.functionname||'',', ') from t_mb_function_menu c 
				where c.functionid in (select * from json_array_elements_text(b.functionidlist::json)) and c.functionid!='000000') as functionlist
			from  t_mb_role_group a
			LEFT JOIN t_mb_role_group_function b ON a.rolegrpid = b.rolegrpid
			inner join t_mb_role_group_account d on d.rolegrpid=a.rolegrpid
			where (d.userid = in_userid or in_userid = '')
				and (in_roleid = '' or a.rolegrpid = in_roleid)
				and (in_rolename = '' or a.rolegrpname ~* in_rolename)
	) AS t;
	--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;

end

 $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100