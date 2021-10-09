-------------------------------------------------
-- 单元测试
-- 查询医生im与sdkappid信息
-------------------------------------------------
select * from f_mb_user_iminfo_query(
	'2e202eff340f47288050901df7530d7a',	--in_userid                  VARCHAR(64),
	'5101003010004',	--in_hospcode           VARCHAR(64),
	
	3,	--IN in_countflag  	int4,
	0,	--IN in_offset 		int4,
	99	--IN in_pagesize 		int4,
)