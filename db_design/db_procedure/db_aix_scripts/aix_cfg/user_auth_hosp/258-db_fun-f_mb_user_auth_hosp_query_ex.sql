CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_query_ex(
	in_userid varchar(64),
	
	OUT retcode integer, OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：查询账号的具有权限的医院。
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功;
 *     retvalue：返回关联表查询字段记录
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	v_rec record;
begin
	
	select array_to_json(array_agg(row_to_json(t.*)))  into retvalue from (
		select distinct a.userid,
				 	a.hospcode, 
				 	b.hospname, 
				 	b.hospalias
		 	from  t_mb_user_auth_hosp_dept a left join t_dict_hospital b
    		ON a.hospcode = b.hospcode 
			where a.userid = in_userid
	) t;
	
	
	if (retcode is NULL) THEN
		retcode := 0;
	end if;
	if (retvalue is NULL) then 
		retvalue := '[]';
	end if;


end

 $function$