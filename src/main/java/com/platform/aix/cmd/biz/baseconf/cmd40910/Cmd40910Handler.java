package com.platform.aix.cmd.biz.baseconf.cmd40910;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.user.role.function.RoleGroupFunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 查询角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40910")
public class Cmd40910Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupFunctionService roleGroupFunctionService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40910Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40910Req req = (Cmd40910Req) request;
        return new APIResponse(this.roleGroupFunctionService.queryRoleGroupFunction(req));
    }
}
