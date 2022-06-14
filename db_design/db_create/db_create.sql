-- 系统参数表 add by advance 2022年5月29日11:29:53
CREATE TABLE SYS_PARA(
systemcode VARCHAR(50) DEFAULT 'ALL' NOT NULL ,
paracode VARCHAR(50) NOT NULL ,
paravalue VARCHAR(1000) NOT NULL ,
datatype VARCHAR(50),
datascope VARCHAR(100),
remark VARCHAR(600),
createuser VARCHAR(20) NOT NULL ,
createdept VARCHAR(32),
createtime TIMESTAMP (6) NOT NULL ,
updateuser VARCHAR(20) NOT NULL ,
updatetime TIMESTAMP (6) NOT NULL ,
institutioncode VARCHAR(50),
updateflg VARCHAR(50) DEFAULT '01' NOT NULL
);
CREATE UNIQUE INDEX "UI_SYS_PARA" ON SYS_PARA (systemcode, paracode, institutioncode) ;
COMMENT ON TABLE sys_para IS '系统参数';
COMMENT ON COLUMN sys_para.systemcode IS '系统代码';
COMMENT ON COLUMN sys_para.paracode IS '参数代码';
COMMENT ON COLUMN sys_para.paravalue IS '参数值';
COMMENT ON COLUMN SYS_PARA.datatype IS '数据类型(01:文字列,02:下拉框,03:日期)';
COMMENT ON COLUMN SYS_PARA.datascope IS '选择范围';
COMMENT ON COLUMN SYS_PARA.remark IS '备注';
COMMENT ON COLUMN SYS_PARA.createuser IS '创建人';
COMMENT ON COLUMN SYS_PARA.createdept IS '创建部门';
COMMENT ON COLUMN SYS_PARA.createtime IS '创建时间';
COMMENT ON COLUMN SYS_PARA.updateuser IS '更新人';
COMMENT ON COLUMN SYS_PARA.updatetime IS '更新时间';
COMMENT ON COLUMN SYS_PARA.institutioncode IS '机构';
COMMENT ON COLUMN SYS_PARA.updateflg IS '用户可更改标识';

-- t_user 测试表
create table t_user(
id varchar(32),
username varchar(256)
);
