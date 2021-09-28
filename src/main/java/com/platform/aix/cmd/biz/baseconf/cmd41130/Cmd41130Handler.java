package com.platform.aix.cmd.biz.baseconf.cmd41130;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.conf.prj.PrjService;
import com.platform.aix.service.conf.prj.bean.TPrj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 查询项目
 *
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_41130")
public class Cmd41130Handler extends CommandBaseHandler {

    @Autowired
    PrjService prjService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd41130Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd41130Req req = (Cmd41130Req) request;
        Page<TPrj> tPrjPage = prjService.queryPrj(req);
        return new APIResponse(tPrjPage);
    }
}
