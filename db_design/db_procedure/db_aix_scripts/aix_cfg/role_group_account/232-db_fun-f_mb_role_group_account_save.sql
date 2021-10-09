CREATE OR REPLACE FUNCTION f_mb_role_group_account_save(
	in_userid varchar(64),
	in_rolegrpidarrayjson text,
	OUT retcode integer, OUT retvalue text)

 RETURNS record
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：保存角色账号关联关系。先删除原来的角色，再插入新的角色
 * 参数：按要求填写参数
 *     in_rolegrpidarrayjson 以josn数组格式， 格式["0001", "0002"]
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
	v_rec_role record;
begin
	
	if (trim(in_userid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	delete from t_mb_role_group_account where userid = in_userid; 
	
	for v_rec in 
	(select * from json_array_elements_text(in_rolegrpidarrayjson::json))
	loop
	
		--循环 角色数组
		for v_rec_role in
			select * from json_array_elements(v_rec.value::json)

		loop 
				raise notice '==>%',v_rec_role.value;
				--多个角色批量插入
				v_value := v_rec_role.value::text;
				v_value := replace(v_value,'"','');
				select count(1) into v_cnt from t_mb_role_group_account 
				where  (userid = in_userid and rolegrpid = v_value );
				if (v_cnt = 0) then
					select now() into v_createdtime;
					v_modifiedtime := v_createdtime;
					insert into t_mb_role_group_account(
							 	userid,
								rolegrpid,
								sysflag,
							   createdtime,
							   modifiedtime)
					values (
							 	in_userid,
								v_value,
								20000,
								v_createdtime,
								 v_modifiedtime);
				end if;
		end loop;
	
	end loop;


	--	处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
