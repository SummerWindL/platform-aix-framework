INSERT INTO aix.t_pikai_sys_dict_type
(dict_code, dict_name, is_fixed)
VALUES('GENDER', '性别', true);
INSERT INTO aix.t_pikai_sys_dict_type
(dict_code, dict_name, is_fixed)
VALUES('YES_NO', '是否', true);
INSERT INTO aix.t_pikai_sys_dict_type
(dict_code, dict_name, is_fixed)
VALUES('ORDER_STATUS', '订单状态', false);
INSERT INTO aix.t_pikai_sys_dict_type
(dict_code, dict_name, is_fixed)
VALUES('ORDER_TYPE', '订单类型', false);
INSERT INTO aix.t_pikai_sys_dict_type
(dict_code, dict_name, is_fixed)
VALUES('EMAIL_VERIFICATION_STATUS', '邮箱验证状态', false);

INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(1, 'GENDER', 'MALE', '男', '1', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(2, 'GENDER', 'FEMALE', '女', '2', 2);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(3, 'YES_NO', 'YES', '是', '0', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(4, 'YES_NO', 'NO', '否', '1', 2);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(5, 'ORDER_STATUS', 'PAID', '已报', '01', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(6, 'ORDER_STATUS', 'PACD', '已成', '02', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(7, 'ORDER_STATUS', 'CANCEL', '已撤单', '03', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(8, 'ORDER_TYPE', 'NORMAL', '普通订单', '01', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(9, 'ORDER_TYPE', 'GROUP', '团购订单', '02', 1);

INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(10, 'EMAIL_VERIFICATION_STATUS', 'PENDING', '待处理', '01', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(11, 'EMAIL_VERIFICATION_STATUS', 'VERIFIED', '已验证', '02', 1);
INSERT INTO aix.t_pikai_sys_dict_item
(id, dict_code, item_code, item_name, item_value, sort)
VALUES(12, 'EMAIL_VERIFICATION_STATUS', 'EXPIRED', '已过期', '03', 1);

