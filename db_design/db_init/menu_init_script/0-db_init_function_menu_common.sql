---------------------------
-- 插入功能菜单（非字典表）
---------------------------
truncate table t_mb_function_menu;
INSERT INTO t_mb_function_menu (functionid,lang,functionname,memo,parentfunctionid,sortid,functionpage,functionicon,visibletype,functionlevel,pcsystype) VALUES
 ('000000','zh','ROOT','','',1,'','',2,0,-1)

/*--------------------------------------竹信管理员功能菜单------------------------------------------------------------------------*/
,('990000','zh','竹信管理','','000000',1,'ZXGL','home',2,1,1)
,('990100','zh','竹信配置','','990000',1,'ZXGL','home',2,2,1)
,('990102','zh','竹信APP服务配置','','990100',3,'RoleCfgPage','network',2,3,1)
;