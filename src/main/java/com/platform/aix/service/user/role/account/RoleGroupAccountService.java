package com.platform.aix.service.user.role.account;

import net.sf.json.JSONObject;

/**
 * 角色帐号关联
 *
 * @author: fyw
 * @date: 2018/8/1
 * @description:
 */
public interface RoleGroupAccountService {

    JSONObject queryRoleGroupAccount(String userid, String rolegroupid);

    void saveRoleGroupAccount(String userid, String rolegrpidarray);

    void deleteRoleGroupAccount(String userid, String rolegroupid);
}
