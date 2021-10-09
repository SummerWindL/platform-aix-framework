


-- ----------------------------
-- Records of t_mb_role_group_function
--	为角色配置功能菜单
-- ----------------------------
--truncate table t_mb_role_group_function;

-- 为管理员添加菜单
delete from t_mb_role_group_function where rolegrpid='10001';
INSERT INTO t_mb_role_group_function (rolegrpid,functionidlist,sysflag,modifiedtime,createdtime)
select '10001',  (select array_to_json(array_agg(t.functionid)) from t_mb_function_menu t) ,10000, now(), now() ;

