package com.platform.aix.cmd.biz.baseconf.cmd40620;

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
 * 删除角色
 *
 * @author: fyw
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40620")
public class Cmd40620Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupService roleGroupService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40620Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40620Req req = (Cmd40620Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupService.deleteDxRoleGroup(req);

        if (0 <= basePlpgsqlModel.getRetcode()) {
            log.info(String.format("%s删除成功！", req.getRolegrpid()));
        } else {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }

        return new APIResponse();
    }
}
