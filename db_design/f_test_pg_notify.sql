
create table ofconversation(
id varchar(64)
);
--触发器函数
CREATE OR REPLACE FUNCTION conversation_notify()
  RETURNS trigger AS
$BODY$
    BEGIN
        perform  pg_notify('mymessage', '{"DoctorName":"医生名称","MachineId":"HST821255555","BloodPressure":{"HighPressure":"96","Pulse":"65","LowPressure":"57","Result":""},"Temperature":{"Temperature":"37","Result":"1"},"Chol":{"Chol":"2.77","Result":"1"},"Urinalysis":{"BIL":0,"PRO":0,"VC":0,"CR":0,"Result":0,"GLU":0,"UCA":0,"URO":0,"SG":0,"PH":0,"NIT":0,"MAL":0,"BLD":0,"KET":0,"LEU":0},"Cardiovascular":{"CMBV":"","HeartFunction1":"","SV":"","HeartFunction2":"","TPR":"","VascularCondition1":"","VascularCondition2":"","CO":"","HOV":"","N":"","Result":"","PAWP":""},"Lung":{"PEF":5.77,"FEF25":5.69,"FVC":3.6,"Lung":"1112","FEV1":3.25,"FEF2575":4.02,"FEF75":2.16,"Result":"0"},"Alcohol":{"errcode":"","AlcoholImg":"null","errinfo":"","Alcohol":"2.0","Result":"0"},"BloodSugar":{"BloodsugarType":"1","BloodSugar":"3.8","Result":"1"},"UnitNo":"4450585525","DoctorId":"医生工号","PEEcg":{"QTc":"","PR":"","QT":"","QRSAxis":"","Hr":"","TAxis":"","SV1":"","Result":"","RV5":"","QRS":"","PAxis":"","EcgData":"","EcgImg":""},"ArteryAve":{"SBP":"0","CSBP":"0","CAPP":"0","DBP":"0","AVI":"0","Pulse":"0","API":"0"},"MacAddr":"00-06-55-55-0A-2B","MinFat":{"Shape":"3","Height":"182","FatRate":"17","Physique":"3","BasicMetabolism":"1157","Weight":"80","Bmi":"24.4","Result":"1"},"BMD":{"PAB":"","TSCORE":"2.0","SOS":"300","EOA":"","RRF":"","YOUNG_ADULT":"","OI":"","BQI":"","ZSCORE":"2.2","AGE_MATCHED":"","BUA":"","Result":""},"Height":{"Height":"180","IdealWeight":"74","Weight":"70","BMI":"22","Result":"2"},"Hb":{"Hct":"6.0","Hb":"5.0","Result":"0"},"LoginType":"1","Member":{"Nation":"","IcCode":"","Address":"广东省XX市","Sex":"1","BarCode":"","Birthday":"1989-09-28","SocialCode":"","Mobile":"15811397368","EndDate":"","Name":"陈XX ","StartDate":"","Department":"","UserID":"","UserIcon":"","IdCode":"361103196005178567","Age":"25"},"Whr":{"Waistline":"22","Whr":"25","Hipline":"88","Result":"1"},"UnitName":"XX医院","RecordNo":"2125555520150409143202","Ecg":{"nGain":"2","Analysis":"","Hr":"88","EcgData":"","Result":""},"Ua":{"Ua":"0.54","Result":"1"},"Bo":{"Oxygen":"99","BpmList":"","EndTime":"2015-04-09 14:32:02","SecondCount":"","OxygenList":"","StartTime":"2015-04-09 14:32:02","Bpm":"","Result":""},"DeviceType":"SK-T8","Fat":{"Water":"26.8","YoutuiFat":"3.0","Minerals":"1.2","Result":"","FatAdjust":"-0.3","ZuotuiMuscle":"9.7","Viscera":"7","YoutuiMuscle":"9.9","Bmc":"3.1","YoubiMuscle":"3.1","WaterRate":"36.4","Muscle":"34.4","Foc":"9","MuscleAdjust":"0.4","ZuobiMuscle":"2.9","MuscleRate":"75.2","QuganMuscle":"30.9","ExceptFat":"49.8","Fic":"17.8","WeightAdjust":"0.1","YoubiFat":"0.7","QuganFat":"8.0","Protein":"7.6","Fat":"26.2","FatRate":"16","ZuotuiFat":"3.0","ZuobiFat":"0.7","BasicMetabolism":"1140"},"MeasureTime":"2015-04-09 14:32:02","BloodFat":{"HdlChol":"3.56","CalcLdl":"2.35","TChol":"2.77","Trig":"4.21","TcHdl":"","Result":"1"}}');
        raise notice '%',11111111111;
        --NOTIFY mymessage, 'fired by NOTIFY';
        RETURN NULL;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION conversation_notify() OWNER TO postgres;

CREATE TRIGGER conversation_notify
  AFTER INSERT OR UPDATE
  ON ofconversation
  FOR EACH ROW
  EXECUTE PROCEDURE conversation_notify();


 insert into ofconversation values ('123');


select * from ofconversation


-----------------------------------------------------测试用例 -----------------------------------------

drop table t_pg_notification_send_log;
create table t_pg_notification_send_log (
   serialid             VARCHAR(64)          not null,
   commercianunit       VARCHAR(64)          null,
   destination          VARCHAR(64)          null,
   params               Jsonb                null,
   createdtime          TIMESTAMP            null,
   constraint PK_T_PG_NOTIFICATION_SEND_LOG primary key (serialid)
);

comment on table t_pg_notification_send_log is
'通知发送日志表  
1.记录通知发送的业务单元  存储发送消息的存储过程名称
2.记录通知发送目标
3.记录通知发送参数';

comment on column t_pg_notification_send_log.serialid is
'唯一id';

comment on column t_pg_notification_send_log.commercianunit is
'发送消息存储过程名称';

comment on column t_pg_notification_send_log.destination is
'目标';

comment on column t_pg_notification_send_log.params is
'发送参数';

comment on column t_pg_notification_send_log.createdtime is
'创建时间';



create or replace function f_test_pg_notify(
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
	select * into v_rec from f_zx_notification_model('f_test_pg_notify','alert_notification','{"notifyno":"cmd_4060001", "notifytype" :"update","notifyparam":{"disecode":"xxx","disename":"xxxx"}}');
	-- 处理返回结果
	retcode := v_rec.retcode;
	retvalue := v_rec.retvalue;

	if (retvalue is NULL) then
		retvalue := '[]';
	end if;

end
$function$;


create or replace function f_test_pg_notify1(
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
	select * into v_rec from f_zx_notification_model('f_test_pg_notify1','ssinfo_notification','{"notifyno":"cmd_4060002", "notifytype" :"update","notifyparam":{"disecode":"xxx","disename":"xxxx"}}');
	-- 处理返回结果
	retcode := v_rec.retcode;
	retvalue := v_rec.retvalue;

	if (retvalue is NULL) then
		retvalue := '[]';
	end if;

end
$function$;


select * from f_test_pg_notify('1');
select * from f_test_pg_notify1('2');
