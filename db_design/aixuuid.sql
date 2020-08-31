CREATE OR REPLACE FUNCTION aixuuid()
 RETURNS text
 LANGUAGE plpgsql
AS $function$

/*
 * 函数说明：生成UUID字符串
 * 参数：按要求填写参数
 *  * 返回：UUID字符串
*/
declare
	--	入参说明
	--	程序变量说明
	v_uuid					TEXT default '';
begin

	-- 生成UUID
	--	SELECT md5('aa:aa:aa:aa:aa:aa' || now() || random()) into v_uuid;
	select lower(array_to_string(array(select substring('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' FROM (ceil(random()*62))::int FOR 1) FROM generate_series(1, 32)), '')) into v_uuid ;

	RETURN v_uuid;
end

 $function$
;
