package com.platform.aix.cmd.biz.baseconf.cmd40610;

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

/**
 * 新增角色
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40610")
public class Cmd40610Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupService roleGroupService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40610Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException {
        Cmd40610Req req = (Cmd40610Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupService.insertDxRoleGroup(req);

        if (0 <= basePlpgsqlModel.getRetcode()) {
            log.info(String.format("%s添加成功！", req.getRolegrpname()));
        } else if (-1 == basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_ROLE_GROUP_EXIST);
        } else if (-6 == basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_ROLE_GROUP_ID_IS_NO_NULL);
        } else {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }

        return new APIResponse();
    }
}
