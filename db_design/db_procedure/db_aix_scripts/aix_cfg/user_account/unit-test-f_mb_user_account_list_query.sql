in_userid varchar(64),
	in_countflag integer, 
	in_offset integer, 
	in_pagesize integer, 
	
select * from f_mb_user_account_list_query('admin','','','','','','',''3,0,1000);

select * from f_mb_user_account_query('admin','admin',3,0,1000);


CREATE OR REPLACE FUNCTION f_mb_auth_hosp_user_account_list_query(
	in_userid varchar(64),
	in_hospcode varchar(64),
	in_doctoruserid varchar(64),
	in_deptcode varchar(64),
	in_hospname varchar(64),
	in_doctorname varchar(64),
	in_deptname varchar(64),
	in_countflag integer, 
	in_offset integer, 
	in_pagesize integer, 
	
select * from f_mb_auth_hosp_user_account_list_query('admin','','','','海西','','',3,0,1000)


select jsonb_build_array(hospcode) from t_mb_hosp

select array_to_json(t) from (
select hospcode from t_mb_hosp
) t

  