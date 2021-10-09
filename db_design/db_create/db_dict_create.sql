/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2020/6/8 15:25:39                            */
/*==============================================================*/


/*==============================================================*/
/* Table: t_dict_area                                           */
/*==============================================================*/
create table t_dict_area (
   regionlevel          INT4                 not null default 6,
   areacode             VARCHAR(64)          not null,
   areaname             VARCHAR(64)          not null,
   areafullname         VARCHAR(1000)        null,
   arealevel            int4                 not null,
   parentareacode       VARCHAR(64)          not null default '',
   lang                 VARCHAR(64)          not null,
   constraint PK_T_DICT_AREA primary key (lang, areacode)
);

comment on table t_dict_area is
'行政区划代码参考《中华人民共和国县以上行政区划代码》';

comment on column t_dict_area.regionlevel is
'机构配置参数 默认6，可以配置为5级';

comment on column t_dict_area.areacode is
'区划编码';

comment on column t_dict_area.areaname is
'区划名称';

comment on column t_dict_area.areafullname is
'区划全称';

comment on column t_dict_area.arealevel is
'-1=国家(默认节点)；0=省；1=市；2=区/县；3=街道办/乡镇；4=村；5=村组';

comment on column t_dict_area.parentareacode is
'上级编码';

comment on column t_dict_area.lang is
'语言代码参考ISO 639-1';

/*==============================================================*/
/* Index: t_dict_area_PK                                        */
/*==============================================================*/
create unique index t_dict_area_PK on t_dict_area (
lang,
areacode
);

/*==============================================================*/
/* Index: t_dict_area_areacode_idx                              */
/*==============================================================*/
create  index t_dict_area_areacode_idx on t_dict_area (
areacode
);

/*==============================================================*/
/* Index: t_dict_area_arealevel_idx                             */
/*==============================================================*/
create  index t_dict_area_arealevel_idx on t_dict_area (
arealevel
);

/*==============================================================*/
/* Index: t_dict_area_parentareacode_idx                        */
/*==============================================================*/
create  index t_dict_area_parentareacode_idx on t_dict_area (
parentareacode
);

/*==============================================================*/
/* Table: t_dict_area_code                                      */
/*==============================================================*/
create table t_dict_area_code (
   areacode             VARCHAR(64)          not null,
   lang                 VARCHAR(64)          not null,
   areaname             VARCHAR(64)          not null,
   parentareacode       VARCHAR(64)          not null default '',
   constraint PK_T_DICT_AREA_CODE primary key (areacode, lang)
);

comment on table t_dict_area_code is
'行政区划代码参考《中华人民共和国县以上行政区划代码》';

comment on column t_dict_area_code.areacode is
'区划编码';

comment on column t_dict_area_code.lang is
'语言代码参考ISO 639-1';

comment on column t_dict_area_code.areaname is
'区划名称';

comment on column t_dict_area_code.parentareacode is
'上级编码';

/*==============================================================*/
/* Index: t_dict_area_code_PK                                   */
/*==============================================================*/
create unique index t_dict_area_code_PK on t_dict_area_code (
areacode,
lang
);

/*==============================================================*/
/* Table: t_dict_ask_item                                       */
/*==============================================================*/
create table t_dict_ask_item (
   qcatetitle           VARCHAR(64)          not null,
   qcatesubtitle        VARCHAR(64)          not null,
   qcode                VARCHAR(64)          not null,
   qname                VARCHAR(1000)        null,
   statisticalname      VARCHAR(1000)        null,
   qparentcode          VARCHAR(64)          null,
   qtypecode            VARCHAR(64)          not null,
   qtypename            VARCHAR(64)          null,
   qitemcode            VARCHAR(64)          not null,
   qitemname            VARCHAR(1000)        null,
   qfiltercode          JSONB                null,
   qitemsortflag        INT4                 null,
   optmutexflag         INT4                 null default 0,
   optmemoflag          INT4                 null default 0,
   optsortflag          INT4                 null,
   optdefaultflag       INT4                 null default 0,
   qpage                INT4                 null,
   vuecomponentname     VARCHAR(64)          null,
   vuecomponenttype     INT4                 null,
   constraint PK_T_DICT_ASK_ITEM primary key (qcode, qcatetitle, qcatesubtitle, qtypecode, qitemcode)
);

comment on table t_dict_ask_item is
'调查问卷字典';

comment on column t_dict_ask_item.qcatetitle is
'分类标题';

comment on column t_dict_ask_item.qcatesubtitle is
'分类子标题';

comment on column t_dict_ask_item.qcode is
'问题编码';

comment on column t_dict_ask_item.qname is
'问题名称';

comment on column t_dict_ask_item.statisticalname is
'统计名称';

comment on column t_dict_ask_item.qparentcode is
'父问题编码';

comment on column t_dict_ask_item.qtypecode is
'问题类型编码';

comment on column t_dict_ask_item.qtypename is
'问题类型名称';

comment on column t_dict_ask_item.qitemcode is
'问题选项编码';

comment on column t_dict_ask_item.qitemname is
'问题选项名称';

comment on column t_dict_ask_item.qfiltercode is
'问题过滤标记';

comment on column t_dict_ask_item.qitemsortflag is
'问题选项排序标记';

comment on column t_dict_ask_item.optmutexflag is
'0=不互斥；非零=与其它不同值的选项互斥；';

comment on column t_dict_ask_item.optmemoflag is
'1=该字典编码允许填写描述；0=不允许填写描述';

comment on column t_dict_ask_item.optsortflag is
'答案选项排序标记';

comment on column t_dict_ask_item.optdefaultflag is
'0=不显示；1=显示';

comment on column t_dict_ask_item.qpage is
'问题所属页码';

comment on column t_dict_ask_item.vuecomponentname is
'业务项目VUE组件名称';

comment on column t_dict_ask_item.vuecomponenttype is
'-1=未定义；
1=简单输入，如文本
2=三元组输入，如：code, name, memo
3=JSON数组输入，如：[{,,,},,,]
';

/*==============================================================*/
/* Index: t_dict_ask_item_PK                                    */
/*==============================================================*/
create unique index t_dict_ask_item_PK on t_dict_ask_item (
qcode,
qcatetitle,
qcatesubtitle,
qtypecode,
qitemcode
);

/*==============================================================*/
/* Table: t_dict_common_code                                    */
/*==============================================================*/
create table t_dict_common_code (
   dicttype             VARCHAR(64)          not null,
   dicttypename         VARCHAR(64)          null,
   dictid               VARCHAR(64)          null,
   dictcode             VARCHAR(64)          not null,
   dictcodename         VARCHAR(255)         not null,
   mutexflag            INT4                 not null default 0,
   memoflag             INT4                 not null default 0,
   constraint PK_T_DICT_COMMON_CODE primary key (dictcode, dicttype)
);

comment on table t_dict_common_code is
'类别字符串格式为  dict_xxxx';

comment on column t_dict_common_code.dicttype is
'字典类型';

comment on column t_dict_common_code.dicttypename is
'字典类型名称';

comment on column t_dict_common_code.dictid is
'dictid=dicttype+dictcode';

comment on column t_dict_common_code.dictcode is
'字典编码';

comment on column t_dict_common_code.dictcodename is
'字典编码名称';

comment on column t_dict_common_code.mutexflag is
'0=不互斥；非零=与其它不同值的选项互斥；';

comment on column t_dict_common_code.memoflag is
'1=该字典编码允许填写描述；0=不允许填写描述';

/*==============================================================*/
/* Index: t_dict_common_code_PK                                 */
/*==============================================================*/
create unique index t_dict_common_code_PK on t_dict_common_code (
dictcode,
dicttype
);



/*==============================================================*/
/* Table: t_dict_hospital                                       */
/*==============================================================*/
create table t_dict_hospital (
   hospid               VARCHAR(64)          null,
   hospcode             VARCHAR(64)          not null,
   hospname             VARCHAR(255)         not null,
   hospalias            VARCHAR(255)         null default '',
   areacode             VARCHAR(64)          not null,
   areaname             VARCHAR(64)          null,
   hosplevelcode        VARCHAR(64)          not null,
   hosplevelname        VARCHAR(64)          null,
   hosptypecode         VARCHAR(64)          null,
   hosptypename         VARCHAR(64)          null,
   address              VARCHAR(255)         null default '',
   hospweb              VARCHAR(255)         null default '',
   resume               TEXT                 null default '',
   contactphone         VARCHAR(64)          null,
   contactname          VARCHAR(64)          null,
   postcode             VARCHAR(64)          null,
   enableflag           INT4                 not null default 10000,
   createdtime          TIMESTAMP            not null default CURRENT_TIMESTAMP,
   modifiedtime         TIMESTAMP            null default CURRENT_TIMESTAMP,
   constraint PK_T_DICT_HOSPITAL primary key (hospcode)
);

comment on table t_dict_hospital is
'所有医疗机构在该字典表统一编码';

comment on column t_dict_hospital.hospid is
'机构ID';

comment on column t_dict_hospital.hospcode is
'医院编码';

comment on column t_dict_hospital.hospname is
'医院名称';

comment on column t_dict_hospital.hospalias is
'医院别名';

comment on column t_dict_hospital.areacode is
'划区编码';

comment on column t_dict_hospital.areaname is
'区划名称';

comment on column t_dict_hospital.hosplevelcode is
'等级编码';

comment on column t_dict_hospital.hosplevelname is
'等级名称';

comment on column t_dict_hospital.hosptypecode is
'机构类别（ 1医院、妇幼保健机构、专科疾病防治机构、疗养院、护理院, 2乡镇卫生院, 3诊所、医务室、卫生所, 4村卫生室 5急救中心、急救站, 6疾病预防控制中心、卫生防疫站、预防保健中心 7卫生监督机构, 8采供血机构、健康教育机构, 9卫生行政机构, 10社区卫生服务站, 11社区卫生服务中心, 12街道卫生院 ）';

comment on column t_dict_hospital.hosptypename is
'类型名称';

comment on column t_dict_hospital.address is
'医院地址';

comment on column t_dict_hospital.hospweb is
'门户';

comment on column t_dict_hospital.resume is
'简介';

comment on column t_dict_hospital.contactphone is
'联系电话';

comment on column t_dict_hospital.contactname is
'联系人';

comment on column t_dict_hospital.postcode is
'邮政编码';

comment on column t_dict_hospital.enableflag is
'10000=正常使用，20000=禁用';

comment on column t_dict_hospital.createdtime is
'创建时间';

comment on column t_dict_hospital.modifiedtime is
'修改时间';

/*==============================================================*/
/* Index: t_dict_hospital_PK                                    */
/*==============================================================*/
create unique index t_dict_hospital_PK on t_dict_hospital (
hospcode
);



