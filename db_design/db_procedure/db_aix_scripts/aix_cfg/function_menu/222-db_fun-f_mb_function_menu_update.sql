CREATE OR REPLACE FUNCTION f_mb_function_menu_update(
   IN "in_functionid" varchar(64),
   IN "in_lang" varchar(64),
   IN "in_functionname" varchar(64),
   IN "in_parentfunctionid" varchar(64),
   IN "in_sortid" int4,
   IN "in_functionpage" varchar(255),
   IN "in_functionicon" varchar(255),
   IN "in_visibletype" int4,
   IN "in_memo" varchar(64),

   OUT retcode integer, 
   OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：修改一个功能菜单。。
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--参数说明

	--	程序变量
    v_cnt 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
begin
	
	if (trim(in_functionid) = '' or trim(in_lang) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	update t_mb_function_menu set
				   functionname = in_functionname,
				   parentfunctionid = in_parentfunctionid,
				   sortid = in_sortid,
				   functionpage = in_functionpage,
				   functionicon = in_functionicon,
				   visibletype = in_visibletype,
				   memo = in_memo,
				   modifiedtime = v_modifiedtime
	where functionid = in_functionid AND lang = in_lang; 

	--处理返回结果
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
