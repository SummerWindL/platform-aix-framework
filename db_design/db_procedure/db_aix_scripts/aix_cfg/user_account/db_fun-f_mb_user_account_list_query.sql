CREATE OR REPLACE FUNCTION f_mb_user_account_list_query(
	in_userid varchar(64),
	in_hospcode varchar(64),
	in_countflag integer, 
	in_offset integer, 
	in_pagesize integer, 
	
   OUT retcode integer, 
   OUT retvalue text)
LANGUAGE plpgsql
AS $function$
/*
* 函数说明：查询医院机构下所有用户信息列表
 * 参数：当所有参数为空串‘’时，默认存储过程赋值
 * 返回：
 *     retcode： 小于0，表示失败；>=0表示成功;当大于0时，返回记录总数 
 *     retvalue：返回关联表查询字段记录
 * 
 *    data: {
 * 			"doctorteamid":"uuid"
 * 		}
 *  备注：
 * 
*/

DECLARE
	v_rec            record;
begin
	
		--	合法性校验
		if (in_userid IS  NULL OR  trim(in_userid) = '') THEN 
			retcode := -6;
			retvalue := '{"error_msg":"field value is null or empty"}';
			return;
		end if;
	
		with c_mb_record as (
						select
							a.userid,
						   a.acc,
						   a.pwd,
						   a.salt,
						   a.enableflag,
						   a.regtime,
						   b.hospcode,
						   c.hospname,
						   b.deptcode,
						   b.doctorlevelcode,
						   b.workdepartment,
						   b.idcardno,
						   b.phone,
						   b.workno,
						   b.signature,
						   b.resume,
						   b.doctorname,
						   b.gendercode,
						   b.gendername,
						   b.certtype,
						   b.certtypename,
						   b.doctorlevelname,
						   b.openflag,
						   b.birth,
						   b.nickname,
						   b.photo,
						   b.areacode,
						   b.address,
						   b.params
						from
							t_mb_user_account a
						left join t_mb_user_info b on
							a.userid = b.userid
						LEFT JOIN t_mb_hosp c on b.hospcode = c.hospcode 
						where
							a.enableflag = '10000' --10000=正常使用，20000=禁用
							and ( in_hospcode ='' or b.hospcode = in_hospcode) 
						ORDER BY b.createdtime  ASC 
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
