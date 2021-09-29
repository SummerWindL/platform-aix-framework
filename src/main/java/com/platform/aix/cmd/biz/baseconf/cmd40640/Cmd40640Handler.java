package com.platform.aix.cmd.biz.baseconf.cmd40640;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.user.role.rolegroup.RoleGroupService;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 修改角色
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40640")
public class Cmd40640Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupService roleGroupService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40640Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40640Req req = (Cmd40640Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupService.updateDxRoleGroup(req);

        if(basePlpgsqlModel.getRetcode()>=0){
            log.info(String.format("%s修改成功！", req.getRolegrpname()));
        }else if(-2==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_ROLE_GROUP_EXIST);
        }else if(-6==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_ROLE_GROUP_ID_IS_NO_NULL);
        }else{
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }

        return new APIResponse();
    }
}
