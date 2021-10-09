CREATE OR REPLACE FUNCTION f_mb_user_account_update(
   in_userid               VARCHAR(64),
   in_acc                  VARCHAR(64),
   in_pwd                  VARCHAR(64),
   in_salt                 VARCHAR(64),
   in_enableflag           INT4,
   
   in_hospcode             VARCHAR(64),
   in_deptcode             VARCHAR(64),
   in_doctorlevelcode      VARCHAR(64),
   in_doctorlevelname      VARCHAR(64),
   in_workdepartment       VARCHAR(256),
   in_idcardno             VARCHAR(64),
   in_phone                VARCHAR(64),
   in_workno               VARCHAR(64),
   in_signature            TEXT,
   in_resume               TEXT,
   in_doctorname           VARCHAR(64),
   in_gendercode           VARCHAR(64),
   in_gendername           VARCHAR(64),
   in_birth                VARCHAR(64) ,
   in_nickname             VARCHAR(64),
   in_photo                TEXT,
   in_certtype 			   VARCHAR(64),
   in_certtypename         VARCHAR(64),	   
   in_areacode             VARCHAR(64),
   in_openflag             integer,
   in_address              TEXT,
   in_params               TEXT,

   in_mail varchar(128),
   OUT retcode integer, 
   OUT retvalue text)
 LANGUAGE plpgsql
AS $function$
/*
 * 函数说明：更新医生的账号（修改或重置医生账号密码）
 * 参数：按要求填写参数
 * 返回：
 *     retcode： 小于0，失败；==0成功
 *     retvalue：返回成功或失败的信息
 * 备注：外部程序调用必须判断retcode的返回值进行处理
*/
declare
	--参数说明
	--程序变量
    v_cnt 		    int4 DEFAULT 0;
    v_phone 		    int4 DEFAULT 0;
	v_createdtime   TIMESTAMP DEFAULT Now();
	v_modifiedtime  TIMESTAMP DEFAULT Now();
	v_birth date;
begin

	if (trim(in_userid) = '' or trim(in_acc) = '') then
		retcode := -6;
		retvalue := '{"error_msg":"field value is null or empty"}';
		return;
	end if;

	if(in_birth = null or in_birth = '') then
		v_birth := null;
	end if;
		v_birth := to_date(in_birth,'yyyy-MM-dd');

	--判断手机号码是否存在
	if (trim(in_phone) != '') then
		select count(1) into v_phone from t_mb_user_info tt where tt.phone=in_phone
		and tt.userid != in_userid;
	
		if (v_phone > 0) then
			retcode := -3;
			retvalue := '{"error_msg":"phone exist"}';
			return;
		end if;	
	end if;
	
	--判断用户名称是否重复
	select count(1) into v_cnt from t_mb_user_account 
	where acc = in_acc AND userid != in_userid;
	if (v_cnt != 0) then
		retcode := -2;
		retvalue := '{"error_msg":"record not exist"}';
		return;
	end if;
	
	--更新
	select now() into v_modifiedtime;
	
	if (trim(in_pwd) = '') then
		update t_mb_user_account set
				   userid = in_userid,
				   acc = in_acc,
				   enableflag = in_enableflag,
				   modifiedtime = v_modifiedtime
		where userid = in_userid;
	else 
		update t_mb_user_account set
						 userid = in_userid,
						 acc = in_acc,
						 pwd = in_pwd,
						 salt = in_salt,
						 enableflag = in_enableflag,
						 modifiedtime = v_modifiedtime
		where userid = in_userid;
	end if;
	

	update t_mb_user_info set
				   hospcode = in_hospcode,
				   deptcode = in_deptcode,
				   doctorlevelcode = in_doctorlevelcode,
				   doctorlevelname = in_doctorlevelname,
				   workdepartment = in_workdepartment,
				   idcardno = in_idcardno,
				   phone = in_phone,
				   workno = in_workno,
				   signature = in_signature,
				   resume = in_resume,
				   doctorname = in_doctorname,
				   gendercode = in_gendercode,
				   gendername = in_gendername,
				   certtype = in_certtype,
				   certtypename = in_certtypename,
				   certnum = in_idcardno,
				   openflag = in_openflag,
				   birth = v_birth,
				   nickname = in_nickname,
				   photo = in_photo,
				   areacode = in_areacode,
				   address = in_address,
				   params = in_params,
				   mail = in_mail,
				   modifiedtime = v_modifiedtime
	where userid = in_userid;
	
	--将用户所属医院默认配置到权限医院
	perform f_mb_user_auth_hosp_insert(
	             in_userid ,
	             in_hospcode
	);			 
		
		
	retcode := 0;
	retvalue := '{"error_msg":"success"}';

end

 $function$
