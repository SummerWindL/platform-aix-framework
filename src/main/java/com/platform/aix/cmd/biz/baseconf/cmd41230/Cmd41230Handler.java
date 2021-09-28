package com.platform.aix.cmd.biz.baseconf.cmd41230;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.conf.prj.PrjTitleService;
import com.platform.aix.service.conf.prj.bean.TPrjTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 查询项目标题
 *
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_41230")
public class Cmd41230Handler extends CommandBaseHandler {

    @Autowired
    PrjTitleService prjTitleService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd41230Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd41230Req req = (Cmd41230Req) request;
        Page<TPrjTitle> tPrjTitlePage = this.prjTitleService.queryPrjTitle(req);
        return new APIResponse(tPrjTitlePage);
    }
}
