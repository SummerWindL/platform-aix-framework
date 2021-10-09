CREATE OR REPLACE FUNCTION f_mb_user_auth_hosp_save(
	in_userid varchar(64),
	in_hospcodearrayjson varchar(64),
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：保存账号医院关联关系。先删除原来的医院，再插入新的医院
 * 参数：按要求填写参数
 *     in_hospcodearrayjson 以josn数组格式， 格式["0001", "0002"]
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
	v_value text DEFAULT '';
	v_rec			RECORD;
begin
	
	if (trim(in_userid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;
	
    delete from t_mb_user_auth_hosp where userid = in_userid; 
   
   for v_rec in 
	(select * from json_array_elements_text(in_hospcodearrayjson::json))
	loop
		v_value := v_rec.value;
		select count(1) into v_cnt from t_mb_user_auth_hosp 
		where  (userid = in_userid and hospcode = v_value );
	
		if (v_cnt = 0) then
			select now() into v_createdtime;
			v_modifiedtime := v_createdtime;
			insert into t_mb_user_auth_hosp(
					 	userid,
						hospcode,
					   createdtime,
					   modifiedtime)
			values (
					 	in_userid,
						v_value,
						v_createdtime,
						v_modifiedtime);
		end if;
	
	end loop;
	
	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
