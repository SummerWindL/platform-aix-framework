package com.platform.aix.cmd.biz.baseconf.cmd40920;

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
 * 添加角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40920")
public class Cmd40920Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupFunctionService roleGroupFunctionService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40920Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40920Req req = (Cmd40920Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupFunctionService.saveRoleGroupFunction(req);

        if (basePlpgsqlModel.getRetcode() < 0) {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }

        return new APIResponse();
    }
}
