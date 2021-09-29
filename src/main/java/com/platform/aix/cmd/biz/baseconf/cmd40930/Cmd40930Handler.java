package com.platform.aix.cmd.biz.baseconf.cmd40930;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.user.role.function.RoleGroupFunctionService;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 删除角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40930")
public class Cmd40930Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupFunctionService roleGroupFunctionService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40930Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40930Req req = (Cmd40930Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupFunctionService.deleteRoleGroupFunction(req);

        if (basePlpgsqlModel.getRetcode() < 0) {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }

        return new APIResponse();
    }
}
