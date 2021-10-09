CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_query_nopage(
	in_userid varchar(64),
	in_hospcode varchar(64),
	OUT retcode integer, OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：查询账号的医院列表。
 * 参数：按要求填写参数，当所有参数为空串''时，查询所有记录，否则查询指定参数的记录
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功;当大于0时，返回记录总数 
 *     retvalue：返回关联表查询字段记录
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
	v_rec record;
begin
	
	select array_to_json(array_agg(row_to_json(t))) into retvalue
	from (
		select  a.userid,
				a.hospcode, 
				b.hospname, 
				b.hospalias,
				a.createdtime,
				a.modifiedtime
		from  t_mb_user_auth_hosp a left join t_dict_hospital b
		ON (a.hospcode = b.hospcode) 
		where (a.userid = in_userid) and (in_hospcode = '' or a.hospcode = in_hospcode )
		order by a.createdtime ASC
    	
	) AS t;
	--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;


end

 $function$