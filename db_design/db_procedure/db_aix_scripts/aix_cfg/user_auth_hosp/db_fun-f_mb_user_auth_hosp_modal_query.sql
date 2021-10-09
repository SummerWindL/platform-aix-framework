CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_modal_query(
	in_userid varchar(64),
	in_hospcode varchar(64),
	in_hospname varchar(64),
	in_hospalias varchar(64),
	in_address varchar(64),
	in_hosplevelname varchar(64),
	
	IN in_countflag  	int4,
	IN in_offset 		int4,
	IN in_pagesize 		int4,
	OUT retcode integer, OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：查询账号的权限医院列表。权限医院modal
 * 参数：按要求填写参数，当所有参数为空串''时，查询所有记录，否则查询指定参数的记录
 * 		in_countflag : 1 查询记录总数和分页的记录，记录总数放在retvalue中；
 *                     2 仅查询记录总数，不查询分页记录，记录总数放在retvalue中，
 * 					   3 仅查询分页记录，不查询记录总数。 retvalue始终=0
 * 		in_offset:    该页记录的起始位置
 * 		in_pagesize：     该页记录的总数
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
	
	if ((in_countflag != 1 and in_countflag != 2 and in_countflag != 3) or 
		in_offset < 0 OR in_pagesize < 0) THEN
		retcode := -7; 
		retvalue:='{"error_msg":"param is not correct"}';
		return;
	end if;
	
	--	虚拟表前缀 c_ 代表对物理表，增加了查询条件
	with c_dx_record as 
	(
		 	select  a.userid,
				 	a.hospcode, 
				 	b.hospname, 
				 	b.hospalias,
				 	b.address,
				 	c.hosplevelcode,
				 	c.lang,
				 	c.hosplevelname,
				 	a.createdtime,
				 	a.modifiedtime
		 	from  t_mb_user_auth_hosp a left join t_dict_hospital b 
    		ON (a.hospcode = b.hospcode) 
    		 left join t_dict_hospital_level c 
    		 on c.hosplevelcode = b.hosplevelcode 
			where (a.userid = in_userid) and (in_hospcode = '' or a.hospcode = in_hospcode )
			and (in_hospname = '' or b.hospname ~* in_hospname) 
			and (in_hospalias = '' or b.hospalias ~* in_hospalias)
			and (in_address = '' or b.address ~* in_address)
			and (in_hosplevelname = '' or c.hosplevelname ~* in_hosplevelname)
    		order by a.createdtime ASC
	),
	--	虚拟表前缀 o_l_c 代表对前定义的虚拟表，增加了记录数限定条件
	c_dx_record_limit as
	(
		select * from c_dx_record  offset in_offset limit in_pagesize
	)

	select 
		case when (in_countflag = 1 or in_countflag = 2)  then 
				(select count(1) from c_dx_record ) end as ret_code, 
		case when (in_countflag = 1 or in_countflag = 3) then 
				(select array_to_json(array_agg(row_to_json(t))) from c_dx_record_limit t ) end as ret_value
	into v_rec;
	
	retcode := v_rec.ret_code;
	retvalue := v_rec.ret_value;
	
	if (retcode is NULL) THEN
		retcode := 0;
	end if;
	if (retvalue is NULL) then 
		retvalue := '[]';
	end if;


end

 $function$