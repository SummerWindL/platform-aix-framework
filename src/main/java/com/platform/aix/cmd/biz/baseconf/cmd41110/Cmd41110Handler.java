package com.platform.aix.cmd.biz.baseconf.cmd41110;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.conf.prj.PrjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 添加项目
 *
 * @author: Advance
 * @date: 2018/8/5
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_41110")
public class Cmd41110Handler extends CommandBaseHandler {

    @Autowired
    PrjService prjService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd41110Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd41110Req req = (Cmd41110Req) request;
        prjService.insertPrj(req);
        return new APIResponse();
    }
}
