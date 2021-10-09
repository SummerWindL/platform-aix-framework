CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_insert(
	in_userid varchar(64),
	in_hospcode varchar(64),
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：插入账号的医院。
 * 参数：按要求填写参数
 * 返回：
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
	
	if (trim(in_userid) = '' or trim(in_hospcode) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;
	
	--判断医院编码是否存在
  select count(1) into v_cnt from t_mb_user_auth_hosp  
  where userid = in_userid and hospcode = in_hospcode;
  if (v_cnt != 0) then
		retcode := -1;
		retvalue := '{"error_msg":"record already exist"}';
		return;
	end if;
	
	select now() into v_createdtime;
	v_modifiedtime := v_createdtime;

	insert into t_mb_user_auth_hosp(
			 	userid,
				hospcode,
			   createdtime,
			   modifiedtime)
	values (
			 	in_userid,
				in_hospcode,
				v_createdtime,
				 v_modifiedtime);

	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
