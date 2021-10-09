CREATE OR REPLACE FUNCTION f_mb_user_account_prj_query(
	in_userid varchar(64),
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
	
	if (trim(in_userid) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	select array_to_json(array_agg(row_to_json(t))) into retvalue
	from (
		 	select 
				a.userid,
				b.projectid,
				b.projectname,
				c.titleid,
				c.titlename,
				c.titlelogo
			from t_mb_user_account_prj_tiltle a 
			inner join t_mb_prj b on a.projectid=b.projectid
			inner join t_mb_prj_title c on a.titleid=c.titleid
			where a.userid=in_userid
	) AS t;
		--	处理空结果集
	if (retvalue IS NULL ) then
		retvalue := '[]';
 	end if;
	--	处理返回结果
	retcode := 0;

end

 $function$