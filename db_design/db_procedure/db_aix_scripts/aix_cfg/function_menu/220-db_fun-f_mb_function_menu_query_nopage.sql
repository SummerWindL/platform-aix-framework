CREATE OR REPLACE FUNCTION "f_mb_function_menu_query_nopage"(
		IN "in_functionid" varchar(64), 
		IN "in_lang" varchar(6), 
		IN "in_functionname" varchar(64), 
		IN "in_parentfunctionid" varchar(64), 
		OUT "retcode" int4, OUT "retvalue" text)
LANGUAGE plpgsql
AS $function$

 /*
 * 函数说明：查询功能列表
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功;当大于0时，返回记录总数 
 *     retvalue：返回关联表查询字段记录
 * 备注：
 * 
*/
declare
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
	v_rec			record;
begin

	
	select array_to_json(array_agg(row_to_json(t))) into retvalue
	from (
		select 
		 	functionid,
		 	lang,
		 	functionname,
		 	parentfunctionid,
		 	sortid,
		 	functionpage,
		 	functionicon,
		 	visibletype,
		 	memo,
		 	functionlevel,
		 	pcsystype,		 	
		 	createdtime,
		 	modifiedtime
		from t_mb_function_menu
		where 
			functionid != '000000' and
			(in_functionid = '' or functionid = in_functionid) and 
			(in_lang = '' or lang = in_lang) and
			(in_functionname = '' or functionname = in_functionname) and
			(in_parentfunctionid = '' or parentfunctionid = in_parentfunctionid)
		order by functionid asc
    	
	) AS t;
	--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;

end
 $function$