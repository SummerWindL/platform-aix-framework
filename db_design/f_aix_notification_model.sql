create or replace function f_aix_notification_model(

in in_functionname varchar(64),
in in_destination varchar(64) , --目标
in in_params text, --参数 
/**
 * 格式 {"notifyno":"40101001", "notifytype" :"insert","notifyparam":{"disecode":"","disename":""}} 
 */

out retcode integer,
out retvalue text
)
language plpgsql
as $function$

declare 

begin 
	
	--判断入参in_params 的格式是否符合要求
	if(in_params::jsonb ?& array['notifyno', 'notifytype','notifyparam'] = false)then 
		retcode := -7;
		retvalue := '{"error_msg":"in_params ['||in_params||'] is not correct"}';
		return;
	end if;
	
	perform pg_notify(in_destination,in_params);
	--插入消息发送记录表
	insert into t_pg_notification_send_log values (aixuuid(),in_functionname,in_destination,in_params::jsonb,now());
	retcode := 0;
	retvalue := '{"error_msg":"success"}';
	
end

$function$;