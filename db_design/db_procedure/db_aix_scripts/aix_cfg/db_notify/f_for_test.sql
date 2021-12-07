/**
 *  for循环测试存储过程
 */
create or replace function for_test(
in in_id varchar(64),

out retcode integer,
out retvalue text
)
language plpgsql
as $function$

declare
v_cnt numeric default 0;
v_rec record ;
begin


	FOR var IN 1..1000 loop
		raise notice 'var的值：%', var;
    	PERFORM  aix.f_test_pg_notify('1');
	END LOOP;
	-- 处理返回结果
	retcode := 0;
	retvalue := '[]';

	if (retvalue is NULL) then
		retvalue := '[]';
	end if;

end
$function$;


select * from for_test('1');