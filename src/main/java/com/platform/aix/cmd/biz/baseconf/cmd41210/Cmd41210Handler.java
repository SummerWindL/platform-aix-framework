package com.platform.aix.cmd.biz.baseconf.cmd41210;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.conf.prj.PrjTitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 添加项目标题
 *
 * @author: Advance
 * @date: 2018/8/5
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_41210")
public class Cmd41210Handler extends CommandBaseHandler {

    @Autowired
    PrjTitleService prjTitleService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd41210Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd41210Req req = (Cmd41210Req) request;
        prjTitleService.insertPrjTitle(req);
        return new APIResponse();
    }
}
