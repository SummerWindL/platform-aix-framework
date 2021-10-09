CREATE OR REPLACE FUNCTION f_mb_user_account_menu_query(in_acc text, in_visibletype integer, OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$  
declare   

BEGIN  
	in_visibletype := 2;    
		
	--	生成查询SQL，指定角色，指定版本，包含的所有权限菜单项（虚拟子查询表）
with c_dx_record as 
(
	select f.* from t_mb_function_menu f where f.functionid in 
	(
		select json_array_elements_text(c.functionidlist::json)
		from t_mb_user_account a, t_mb_role_group_account b, t_mb_role_group_function c
		where (a.userid = b.userid)  
		and (b.rolegrpid = c.rolegrpid) 
		and (a.acc = in_acc )		--	查询指定帐号所在角色组所能查看的功能菜单
	) and f.visibletype = in_visibletype  --	查询指定版本所能查看的功能菜单	
)
select array_to_json(array_agg(row_to_json(tt))) into retvalue from (
	select tt.* from 
		(
			select
				distinct d.functionid
			from c_dx_record a
			inner join c_dx_record b on
				a.functionid = b.parentfunctionid
				or a.functionid = b.functionid
			inner join c_dx_record c on
				b.functionid = c.parentfunctionid
				or b.functionid = c.functionid
			inner join c_dx_record d on
				c.functionid = d.parentfunctionid
				or c.functionid = d.functionid
			where
				a.functionid = '000000'	--	需指定树状菜单虚拟根节点
		) t
		left join c_dx_record tt on
			t.functionid = tt.functionid
		order by tt.functionid, tt.sortid
	) tt;
	
	retcode := 0;		--	返回成功

	--	异常处理（暂时没考虑好如何处理）
	EXCEPTION
			WHEN others THEN
			RAISE EXCEPTION '(%)', SQLERRM;
			retcode := -1;	--	返回异常
 	END 
 	
 $function$
