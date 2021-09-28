package com.platform.aix.cmd.biz.baseconf.cmd44110;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.user.account.UserAccountService;
import com.platform.aix.service.user.role.rolegroup.bean.RoleGroupFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.IOException;


/**
 * 查询用户角色
 */
@Controller(Constants.CLIENT_REQ_URL + "/cmd_44110")
public class Cmd44110Handler extends CommandBaseHandler {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd44110Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd44110Req req = (Cmd44110Req) request;
        RoleGroupFilter roleGroupFilter = new RoleGroupFilter();
        BeanUtils.copyProperties(req, roleGroupFilter);
        Page<Cmd44110Resp> doctorList = userAccountService.queryUserAccountRole(roleGroupFilter);
        return new APIResponse(doctorList);
    }
}
