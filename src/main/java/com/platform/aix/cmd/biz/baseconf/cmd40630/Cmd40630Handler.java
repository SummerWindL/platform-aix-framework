package com.platform.aix.cmd.biz.baseconf.cmd40630;

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
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 查询角色
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40630")
public class Cmd40630Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupService roleGroupService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40630Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd40630Req req = (Cmd40630Req) request;
        BasePlpgsqlModel basePlpgsqlModel = this.roleGroupService.queryDxRoleGroup(req);

        Cmd40630Resp[] cmd40630Resps = null;
        try {
            if (0 > basePlpgsqlModel.getRetcode()) {
                throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
            }
            cmd40630Resps = basePlpgsqlModel.getModel(Cmd40630Resp[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Cmd40630Resp> tlist = CollectionUtils.arrayToList(cmd40630Resps);
        Page<Cmd40630Resp> cmd40630RespsPage = new PageImpl<Cmd40630Resp>(tlist,PageRequest.of(0,1),tlist.size());
        return new APIResponse(cmd40630RespsPage);
    }
}
