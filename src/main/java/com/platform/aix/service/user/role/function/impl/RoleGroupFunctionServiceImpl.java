package com.platform.aix.service.user.role.function.impl;

import com.platform.aix.cmd.biz.baseconf.cmd40910.Cmd40910Req;
import com.platform.aix.cmd.biz.baseconf.cmd40920.Cmd40920Req;
import com.platform.aix.cmd.biz.baseconf.cmd40930.Cmd40930Req;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.user.role.function.RoleGroupFunctionService;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.user.menu.FunctionMenuRepository;
import com.platform.repo.pg.repo.user.role.function.RoleGroupFunctionRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 角色组功能关联
 *
 * @author: fyw
 * @date: 2018/8/1
 * @description:
 */
@Service
public class RoleGroupFunctionServiceImpl implements RoleGroupFunctionService {

    @Autowired
    RoleGroupFunctionRepository roleGroupFunctionRepository;
    @Autowired
    FunctionMenuRepository mbFunctionMenuRepository;

    @Override
    public JSONObject queryRoleGroupFunction(Cmd40910Req cmd40910Req) {
        JSONObject json = new JSONObject();

        BasePlpgsqlModel bpmInfo = roleGroupFunctionRepository.queryDxRoleGroupFunction(cmd40910Req.getRolegrpid());
        // BasePlpgsqlModel bpmTree = this.mbFunctionMenuRepository.queryTreeMenu("zh");
        BasePlpgsqlModel bpmTree = this.mbFunctionMenuRepository.queryFunctionMenuNopage("", "zh", "", "");
        try {
            if (0 > bpmInfo.getRetcode() || 0 > bpmTree.getRetcode()) {
                throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
            }
            JSONObject[] jsonInfo = bpmInfo.getModel(JSONObject[].class);
            JSONObject[] jsonTree = bpmTree.getModel(JSONObject[].class);
            json.put("info", jsonInfo);
            json.put("tree", jsonTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public BasePlpgsqlModel saveRoleGroupFunction(Cmd40920Req cmd40920Req) {
        System.out.println(cmd40920Req.getRolegrpid());
        System.out.println(cmd40920Req.getFunctionidlistjosn());
        return roleGroupFunctionRepository.saveDxRoleGroupFunction(cmd40920Req.getRolegrpid(), cmd40920Req.getFunctionidlistjosn());
    }

    @Override
    public BasePlpgsqlModel deleteRoleGroupFunction(Cmd40930Req cmd40930Req) {
        return roleGroupFunctionRepository.deleteDxRoleGroupFunction(cmd40930Req.getRolegrpid());
    }
}
