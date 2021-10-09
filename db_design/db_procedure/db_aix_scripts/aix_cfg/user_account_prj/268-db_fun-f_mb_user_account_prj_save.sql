CREATE OR REPLACE FUNCTION f_mb_user_account_prj_save(
	in_userid varchar(64),
	in_projectid varchar(64),
	in_titleid varchar(64),
	OUT retcode integer, OUT retvalue text)
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：查询账号项目关联关系。
 * 参数：按要求填写参数，当所有参数为空串''时，查询所有记录，否则查询指定参数的记录
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回账号和角色关联记录集,有可能为空
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
begin
	
	if (trim(in_userid) = '' OR trim(in_projectid) = '' OR trim(in_titleid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	delete from t_mb_user_account_prj_tiltle where userid = in_userid; 
	
	insert into t_mb_user_account_prj_tiltle(
			 	userid,
				projectid,
				titleid,
			    createdtime,
			    modifiedtime)
	values (
			 	in_userid,
				in_projectid,
				in_titleid,
				v_createdtime,
				v_modifiedtime);
	
   
		--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;

end

 $function$