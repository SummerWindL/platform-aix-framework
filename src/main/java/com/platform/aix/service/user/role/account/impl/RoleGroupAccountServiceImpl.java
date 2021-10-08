package com.platform.aix.service.user.role.account.impl;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.role.account.RoleGroupAccountService;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.user.role.RoleGroupRepository;
import com.platform.repo.pg.repo.user.role.account.RoleGroupAccountRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色帐号关联
 *
 * @author: Advance
 * @date: 2018/8/1
 * @description:
 */
@Service
public class RoleGroupAccountServiceImpl extends AbstractServiceImpl implements RoleGroupAccountService {

    @Autowired
    RoleGroupAccountRepository mbRoleGroupAccountRepository;

    @Autowired
    RoleGroupRepository mbRoleGroupRepository;

    @Override
    public JSONObject queryRoleGroupAccount(String userid, String rolegroupid) {

        // 查询
        BasePlpgsqlModel bpmInfo = mbRoleGroupAccountRepository.queryDxRoleGroupAccount(userid, rolegroupid);
        if (bpmInfo.getRetcode() < 0) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        BasePlpgsqlModel bpmTree = this.mbRoleGroupRepository.queryDxRoleGroup("",0,1,1);
        if (bpmTree.getRetcode() < 0) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        JSONObject json = new JSONObject();
        json.put("info", basePlpgsqlModel2Clz(bpmInfo, JSONObject[].class));
        json.put("tree", basePlpgsqlModel2Clz(bpmTree, JSONObject[].class));
        return json;
    }

    @Override
    public void saveRoleGroupAccount(String userid, String rolegrpidarray) {
        BasePlpgsqlModel basePlpgsqlModel = mbRoleGroupAccountRepository.saveDxRoleGroupAccount(userid, rolegrpidarray);
        if (basePlpgsqlModel.getRetcode() < 0) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public void deleteRoleGroupAccount(String userid, String rolegroupid) {
        BasePlpgsqlModel basePlpgsqlModel = mbRoleGroupAccountRepository.deleteDxRoleGroupAccount(userid, rolegroupid);
        if (basePlpgsqlModel.getRetcode() < 0) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }
}
