CREATE OR REPLACE FUNCTION f_mb_user_account_check(
			in_acc text,
			in_pwd text,
			OUT retcode integer, OUT retvalue text)
 RETURNS record
 LANGUAGE plpgsql
AS $function$ declare r_account RECORD;

 v_pwd text;

 salt text;

 md5Str text;

 begin
	 select * into r_account from t_mb_user_account a where a.acc = in_acc;

	 if (r_account is null) then
		retcode := -4;
		retvalue := '{"error_msg":"account or pwd not correct"}';
		-- 账号不存在
		return;
	end if;

	if (r_account.pwd != md5(concat( in_pwd, r_account.salt ))) then
		retcode := -4;
		retvalue := '{"error_msg":"account or pwd not correct"}';
		return;
	end if;

	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end;

 $function$
