/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2020/7/1 17:00:44                            */
/*==============================================================*/


/*==============================================================*/
/* Table: t_nethosp_app_init_cfg                                */
/*==============================================================*/
create table t_nethosp_app_init_cfg (
   applicationid        VARCHAR(64)          not null,
   apptypecode          VARCHAR(64)          null,
   apptypename          VARCHAR(64)          null,
   appname              VARCHAR(255)         null,
   applogo              TEXT                 null,
   apploginurl          TEXT                 null,
   appstarturpimageurl  TEXT                 null,
   appstarturpimage     TEXT                 null,
   regsiterprotourl     TEXT                 null,
   regsiterproto        TEXT                 null,
   appmaincolor         varchar(255)         null,
   appselectedcolor     varchar(255)         null,
   remark               TEXT                 null,
   createdtime          TIMESTAMP            null,
   modifiedtime         TIMESTAMP            null,
   constraint PK_T_NETHOSP_APP_INIT_CFG primary key (applicationid)
);

comment on table t_nethosp_app_init_cfg is
'界面上做成列表。增删改查';

comment on column t_nethosp_app_init_cfg.applicationid is
'applicationid';

comment on column t_nethosp_app_init_cfg.apptypecode is
'1 = 互联网医院用户app
2 = 互联网医院医生app';

comment on column t_nethosp_app_init_cfg.apptypename is
'app类型名称';

comment on column t_nethosp_app_init_cfg.appname is
'app名称';

comment on column t_nethosp_app_init_cfg.applogo is
'base64编码';

comment on column t_nethosp_app_init_cfg.apploginurl is
'APP登录地址';

comment on column t_nethosp_app_init_cfg.appstarturpimageurl is
'app启动页图像地址';

comment on column t_nethosp_app_init_cfg.appstarturpimage is
'图片采用base64编码';

comment on column t_nethosp_app_init_cfg.regsiterprotourl is
'注册协议url';

comment on column t_nethosp_app_init_cfg.regsiterproto is
'Html文件';

comment on column t_nethosp_app_init_cfg.appmaincolor is
'app主题颜色';

comment on column t_nethosp_app_init_cfg.appselectedcolor is
'按钮选中颜色';

comment on column t_nethosp_app_init_cfg.remark is
'备注';

comment on column t_nethosp_app_init_cfg.createdtime is
'创建时间';

comment on column t_nethosp_app_init_cfg.modifiedtime is
'修改时间';

/*==============================================================*/
/* Index: t_app_cfg_resist_hosp_PK3                             */
/*==============================================================*/
create unique index t_app_cfg_resist_hosp_PK3 on t_nethosp_app_init_cfg (
applicationid
);

/*==============================================================*/
/* Table: t_mb_hosp                                             */
/*==============================================================*/
create table t_mb_hosp (
   hospcode             VARCHAR(64)          not null,
   hospname             VARCHAR(64)          not null,
   hospalias            VARCHAR(64)          null default '',
   hosplevelcode        VARCHAR(64)          not null,
   enableflag           VARCHAR(64)          not null default '1',
   hospflag             INT4                 null default 0,
   hospinternetflag     INT4                 null default 0,
   hospregisterflag     INT4                 null default 0,
   pcsystype            INT4                 null,
   reserveflag          INT4                 null default 0,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_HOSP primary key (hospcode)
);

comment on table t_mb_hosp is
'15-2-1 医院表';

comment on column t_mb_hosp.hospcode is
'医院编码';

comment on column t_mb_hosp.hospname is
'医院名称';

comment on column t_mb_hosp.hospalias is
'医院别名';

comment on column t_mb_hosp.hosplevelcode is
'医院等级编码';

comment on column t_mb_hosp.enableflag is
'参考 dict_enableflag 字典表定义 
1=正常 2=禁用 3=删除';

comment on column t_mb_hosp.hospflag is
'0 未开通， 1开通';

comment on column t_mb_hosp.hospinternetflag is
'0 未开通， 1开通';

comment on column t_mb_hosp.hospregisterflag is
'0 未开通， 1开通';

comment on column t_mb_hosp.pcsystype is
'0,公共卫生系统，1专科慢病系统';

comment on column t_mb_hosp.reserveflag is
'保留标记位';

comment on column t_mb_hosp.createdtime is
'创建时间';

comment on column t_mb_hosp.modifiedtime is
'修改时间';

/*==============================================================*/
/* Table: t_mb_hosp_dept                                        */
/*==============================================================*/
create table t_mb_hosp_dept (
   hospcode             VARCHAR(64)          not null,
   deptcode             VARCHAR(64)          not null,
   deptname             VARCHAR(255)         not null,
   deptspeciality       TEXT                 null,
   deptkeyword          JSONB                null default '[]',
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_HOSP_DEPT primary key (hospcode, deptcode)
);

comment on table t_mb_hosp_dept is
'医院科室表，每个医院都有自己的科室编码表';

comment on column t_mb_hosp_dept.hospcode is
'医院编码';

comment on column t_mb_hosp_dept.deptcode is
'科室编码';

comment on column t_mb_hosp_dept.deptname is
'科室名称';

comment on column t_mb_hosp_dept.deptspeciality is
'科室专长';

comment on column t_mb_hosp_dept.deptkeyword is
'科室关键字';

comment on column t_mb_hosp_dept.modifiedtime is
'修改时间';

comment on column t_mb_hosp_dept.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_hosp_cfg                                         */
/*==============================================================*/
create table t_mb_hosp_cfg (
   hospcode             VARCHAR(64)          not null,
   cfgtype              VARCHAR(64)          not null,
   cfgtypename          VARCHAR(256)         null,
   cfgname              TEXT                 not null,
   cfgparam             TEXT                 null,
   cfgmemo              TEXT                 null,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_HOSP_CFG primary key (hospcode, cfgtype, cfgname)
);

comment on table t_mb_hosp_cfg is
'医院相关个性化配置参数采用json格式保存在cfgparam字段，具体配置类型定义参考医院配置类型定义文档。
';

comment on column t_mb_hosp_cfg.hospcode is
'医院编码';

comment on column t_mb_hosp_cfg.cfgtype is
'101	病种管理
201	签约合同模版
202	确诊报告模版
203	治疗方案模版
204	随访报告模版
205	个人健康报告模版
';

comment on column t_mb_hosp_cfg.cfgtypename is
'配置类型名称';

comment on column t_mb_hosp_cfg.cfgname is
'配置名称';

comment on column t_mb_hosp_cfg.cfgparam is
'配置参数';

comment on column t_mb_hosp_cfg.cfgmemo is
'配置备注';

comment on column t_mb_hosp_cfg.createdtime is
'创建时间';

comment on column t_mb_hosp_cfg.modifiedtime is
'修改时间';

/*==============================================================*/
/* Index: t_mb_hosp_cfg_PK                                      */
/*==============================================================*/
create unique index t_mb_hosp_cfg_PK on t_mb_hosp_cfg (
hospcode,
cfgtype,
cfgname
);

/*==============================================================*/
/* Table: t_mb_function_menu                                    */
/*==============================================================*/
create table t_mb_function_menu (
   functionid           VARCHAR(64)          not null,
   lang                 VARCHAR(6)           not null,
   functionname         VARCHAR(64)          not null,
   memo                 VARCHAR(64)          not null default '',
   parentfunctionid     VARCHAR(64)          not null,
   sortid               INT4                 not null default 1,
   functionpage         VARCHAR(255)         not null default '‘’',
   functionicon         VARCHAR(255)         not null default '',
   visibletype          INT4                 not null default 2,
   functionlevel        INT4                 not null default 0,
   pcsystype            INT4                 null,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_FUNCTION_MENU primary key (functionid, lang)
);

comment on table t_mb_function_menu is
'功能菜单表';

comment on column t_mb_function_menu.functionid is
'功能ID';

comment on column t_mb_function_menu.lang is
'语言编码';

comment on column t_mb_function_menu.functionname is
'功能名称';

comment on column t_mb_function_menu.memo is
'描述';

comment on column t_mb_function_menu.parentfunctionid is
'父功能ID';

comment on column t_mb_function_menu.sortid is
'排序ID';

comment on column t_mb_function_menu.functionpage is
'功能页面';

comment on column t_mb_function_menu.functionicon is
'功能图标';

comment on column t_mb_function_menu.visibletype is
'功能显示类型';

comment on column t_mb_function_menu.functionlevel is
'默认=0';

comment on column t_mb_function_menu.pcsystype is
'0,公共卫生系统，1专科慢病系统';

comment on column t_mb_function_menu.modifiedtime is
'修改时间';

comment on column t_mb_function_menu.createdtime is
'创建时间';

/*==============================================================*/
/* Index: t_mb_function_menu_idx1                               */
/*==============================================================*/
create  index t_mb_function_menu_idx1 on t_mb_function_menu (
functionid,
parentfunctionid,
sortid
);


/*==============================================================*/
/* Table: t_mb_prj                                              */
/*==============================================================*/
create table t_mb_prj (
   projectid            VARCHAR(64)          not null,
   hospcode             VARCHAR(64)          not null,
   projectname          VARCHAR(64)          not null,
   projectdesc          VARCHAR(255)         null,
   modifiedtime         TIMESTAMP            not null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_PRJ primary key (projectid)
);

comment on table t_mb_prj is
'15-6-1 项目表';

comment on column t_mb_prj.projectid is
'项目ID';

comment on column t_mb_prj.hospcode is
'医院编码';

comment on column t_mb_prj.projectname is
'项目名称';

comment on column t_mb_prj.projectdesc is
'项目描述';

comment on column t_mb_prj.modifiedtime is
'修改时间';

comment on column t_mb_prj.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_prj_title                                        */
/*==============================================================*/
create table t_mb_prj_title (
   projectid            VARCHAR(64)          not null,
   titleid              VARCHAR(64)          not null,
   titlename            VARCHAR(255)         not null,
   titlelogo            TEXT                 null,
   modifiedtime         TIMESTAMP            not null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_PRJ_TITLE primary key (projectid, titleid)
);

comment on table t_mb_prj_title is
'15-6-2 项目标题表';

comment on column t_mb_prj_title.projectid is
'项目ID';

comment on column t_mb_prj_title.titleid is
'标题ID';

comment on column t_mb_prj_title.titlename is
'标题名称';

comment on column t_mb_prj_title.titlelogo is
'图片使用base64编码';

comment on column t_mb_prj_title.modifiedtime is
'修改时间';

comment on column t_mb_prj_title.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_role_group                                       */
/*==============================================================*/
create table t_mb_role_group (
   rolegrpid            VARCHAR(64)          not null,
   rolegrpname          VARCHAR(64)          not null,
   sysflag              INT4                 not null default 20000,
   rolegrpicon          TEXT                 null,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_ROLE_GROUP primary key (rolegrpid)
);

comment on table t_mb_role_group is
'15-4-1  角色表';

comment on column t_mb_role_group.rolegrpid is
'角色ID';

comment on column t_mb_role_group.rolegrpname is
'角色名称';

comment on column t_mb_role_group.sysflag is
'10000=系统管理员 20000=用户';

comment on column t_mb_role_group.rolegrpicon is
'角色图标';

comment on column t_mb_role_group.modifiedtime is
'修改时间';

comment on column t_mb_role_group.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_role_group_account                               */
/*==============================================================*/
create table t_mb_role_group_account (
   userid               VARCHAR(64)          not null,
   rolegrpid            VARCHAR(64)          not null,
   sysflag              INT4                 not null default 20000,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_ROLE_GROUP_ACCOUNT primary key (userid, rolegrpid)
);

comment on table t_mb_role_group_account is
'如果一个帐号存在多个角色，帐号登录成功后把角色名称列出来让用户选择';

comment on column t_mb_role_group_account.userid is
'用户ID';

comment on column t_mb_role_group_account.rolegrpid is
'角色ID';

comment on column t_mb_role_group_account.sysflag is
'10000=系统管理员 20000=用户';

comment on column t_mb_role_group_account.modifiedtime is
'修改时间';

comment on column t_mb_role_group_account.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_role_group_function                              */
/*==============================================================*/
create table t_mb_role_group_function (
   rolegrpid            VARCHAR(64)          not null,
   functionidlist       TEXT                 null,
   sysflag              INT4                 not null default 20000,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_ROLE_GROUP_FUNCTION primary key (rolegrpid)
);

comment on table t_mb_role_group_function is
'15-4-2  角色功能关联表';

comment on column t_mb_role_group_function.rolegrpid is
'角色ID';

comment on column t_mb_role_group_function.functionidlist is
'多个功能ID间用逗号分隔';

comment on column t_mb_role_group_function.sysflag is
'10000=系统管理员 20000=用户';

comment on column t_mb_role_group_function.modifiedtime is
'修改时间';

comment on column t_mb_role_group_function.createdtime is
'创建时间';


/*==============================================================*/
/* Table: t_mb_user_account                                     */
/*==============================================================*/
create table t_mb_user_account (
   userid               VARCHAR(64)          not null,
   acc                  VARCHAR(64)          not null default '',
   pwd                  VARCHAR(64)          not null,
   salt                 VARCHAR(64)          not null,
   enableflag           VARCHAR(64)          not null default '1',
   sysflag              INT4                 not null default 20000,
   hospauthflag         INT4                 null default 1,
   deptauthflag         INT4                 null default 0,
   doctorauthflag       INT4                 null default 0,
   areaauthflag         INT4                 null default 1,
   reserveauthflag      INT4                 null default 0,
   regtime              TIMESTAMP            null default CURRENT_TIMESTAMP,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_USER_ACCOUNT primary key (userid)
);

comment on table t_mb_user_account is
'帐号不允许修改，只能逻辑删除';

comment on column t_mb_user_account.userid is
'用户ID';

comment on column t_mb_user_account.acc is
'帐号';

comment on column t_mb_user_account.pwd is
'密码';

comment on column t_mb_user_account.salt is
'加密因子';

comment on column t_mb_user_account.enableflag is
'10000=正常 20000=禁用';

comment on column t_mb_user_account.sysflag is
'10000=系统管理员 20000=用户';

comment on column t_mb_user_account.hospauthflag is
'0,查看所有医院， 1查看指定机构';

comment on column t_mb_user_account.deptauthflag is
'0,查看所有科室， 1查看指定科室';

comment on column t_mb_user_account.doctorauthflag is
'0,查看所有医生患者 1仅查看责任医生患者';

comment on column t_mb_user_account.areaauthflag is
'0 查看所有划区，1查看指定划区';

comment on column t_mb_user_account.reserveauthflag is
'保留标记';

comment on column t_mb_user_account.regtime is
'注册时间';

comment on column t_mb_user_account.modifiedtime is
'修改时间';

comment on column t_mb_user_account.createdtime is
'创建时间';

/*==============================================================*/
/* Index: t_mb_user_account_PK                                  */
/*==============================================================*/
create unique index t_mb_user_account_PK on t_mb_user_account (
userid
);

/*==============================================================*/
/* Index: t_mb_user_account_idx1                                */
/*==============================================================*/
create  index t_mb_user_account_idx1 on t_mb_user_account (
acc
);

/*==============================================================*/
/* Table: t_mb_user_account_prj_tiltle                          */
/*==============================================================*/
create table t_mb_user_account_prj_tiltle (
   userid               VARCHAR(64)          not null,
   projectid            VARCHAR(64)          not null,
   titleid              VARCHAR(64)          not null,
   modifiedtime         TIMESTAMP            not null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_USER_ACCOUNT_PRJ_TILTL primary key (userid, projectid, titleid)
);

comment on table t_mb_user_account_prj_tiltle is
'15-5-3 帐号项目标题表';

comment on column t_mb_user_account_prj_tiltle.userid is
'用户ID';

comment on column t_mb_user_account_prj_tiltle.projectid is
'项目ID';

comment on column t_mb_user_account_prj_tiltle.titleid is
'标题ID';

comment on column t_mb_user_account_prj_tiltle.modifiedtime is
'修改时间';

comment on column t_mb_user_account_prj_tiltle.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_user_auth_hosp                                   */
/*==============================================================*/
create table t_mb_user_auth_hosp (
   userid               VARCHAR(64)          not null,
   hospcode             VARCHAR(64)          not null,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_USER_AUTH_HOSP primary key (hospcode, userid)
);

comment on table t_mb_user_auth_hosp is
'15-5-5 帐号医院权限';

comment on column t_mb_user_auth_hosp.userid is
'用户ID';

comment on column t_mb_user_auth_hosp.hospcode is
'医院编码';

comment on column t_mb_user_auth_hosp.modifiedtime is
'修改时间';

comment on column t_mb_user_auth_hosp.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_user_auth_hosp_dept                              */
/*==============================================================*/
create table t_mb_user_auth_hosp_dept (
   userid               VARCHAR(64)          not null,
   hospcode             VARCHAR(64)          not null,
   deptcode             VARCHAR(64)          not null,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_USER_AUTH_HOSP_DEPT primary key (userid, hospcode, deptcode)
);

comment on table t_mb_user_auth_hosp_dept is
'同科室帐号不同角色的数据如何查看？
默认情况下帐号只能看到自己建立的档案，对帐号授医院、科室权限（一对多），先查询帐号赋予院科权限档案列表A，再查询该帐号居民档案列表B，A合并B的集合才是该帐号的居民档案权限集合
';

comment on column t_mb_user_auth_hosp_dept.userid is
'用户ID';

comment on column t_mb_user_auth_hosp_dept.hospcode is
'医院编码';

comment on column t_mb_user_auth_hosp_dept.deptcode is
'科室编码';

comment on column t_mb_user_auth_hosp_dept.modifiedtime is
'修改时间';

comment on column t_mb_user_auth_hosp_dept.createdtime is
'创建时间';

/*==============================================================*/
/* Table: t_mb_user_info                                        */
/*==============================================================*/
create table t_mb_user_info (
   userid               VARCHAR(64)          not null,
   acc                  VARCHAR(64)          null,
   hospcode             VARCHAR(64)          not null,
   deptcode             VARCHAR(64)          null,
   doctorno             VARCHAR(64)          null,
   doctorlevelcode      VARCHAR(64)          null,
   doctorlevelname      VARCHAR(64)          null,
   workdepartment       VARCHAR(256)         null,
   workno               VARCHAR(64)          null,
   idcardno             VARCHAR(64)          null default '',
   phone                VARCHAR(64)          null,
   openflag             INT4                 null default 0,
   certtype             VARCHAR(64)          null,
   certtypename         VARCHAR(64)          null,
   certnum              VARCHAR(64)          null,
   signature            TEXT                 null default '',
   resume               TEXT                 null,
   doctorname           VARCHAR(64)          null,
   gendercode           VARCHAR(64)          null,
   gendername           VARCHAR(64)          null,
   birth                DATE                 null,
   mail                 VARCHAR(128)         null,
   nickname             VARCHAR(64)          null,
   photo                TEXT                 null,
   areacode             VARCHAR(64)          null,
   address              TEXT                 null,
   params               TEXT                 null,
   createdtime          TIMESTAMP            null default CURRENT_TIMESTAMP,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_MB_USER_INFO primary key (userid)
);

comment on table t_mb_user_info is
'帐号资料表';

comment on column t_mb_user_info.userid is
'用户ID';

comment on column t_mb_user_info.acc is
'帐号';

comment on column t_mb_user_info.hospcode is
'医院编码';

comment on column t_mb_user_info.deptcode is
'科室编码';

comment on column t_mb_user_info.doctorno is
'执医证号';

comment on column t_mb_user_info.doctorlevelcode is
'职级编码';

comment on column t_mb_user_info.doctorlevelname is
'职级名称';

comment on column t_mb_user_info.workdepartment is
'工作单位';

comment on column t_mb_user_info.workno is
'医生工号';

comment on column t_mb_user_info.idcardno is
'身份证号';

comment on column t_mb_user_info.phone is
'手机号';

comment on column t_mb_user_info.openflag is
'0=不公开 1=公开';

comment on column t_mb_user_info.certtype is
'CV02.01.101 身份证件类别编码字典表 dict_certtype';

comment on column t_mb_user_info.certtypename is
'证件类型名称';

comment on column t_mb_user_info.certnum is
'证件号码';

comment on column t_mb_user_info.signature is
'签名';

comment on column t_mb_user_info.resume is
'个人简介';

comment on column t_mb_user_info.doctorname is
'医生姓名';

comment on column t_mb_user_info.gendercode is
'性别字典表 dict_gender';

comment on column t_mb_user_info.gendername is
'性别名称';

comment on column t_mb_user_info.birth is
'出生日期';

comment on column t_mb_user_info.mail is
'邮箱';

comment on column t_mb_user_info.nickname is
'昵称';

comment on column t_mb_user_info.photo is
'头像';

comment on column t_mb_user_info.areacode is
'区域编码';

comment on column t_mb_user_info.address is
'详细地址';

comment on column t_mb_user_info.params is
'个性化参数';

comment on column t_mb_user_info.createdtime is
'创建时间';

comment on column t_mb_user_info.modifiedtime is
'修改时间';

/*==============================================================*/
/* Index: t_mb_user_info_idx1                                   */
/*==============================================================*/
create  index t_mb_user_info_idx1 on t_mb_user_info (
acc
);

/*==============================================================*/
/* Index: t_mb_user_info_idx2                                   */
/*==============================================================*/
create  index t_mb_user_info_idx2 on t_mb_user_info (
hospcode,
userid
);


