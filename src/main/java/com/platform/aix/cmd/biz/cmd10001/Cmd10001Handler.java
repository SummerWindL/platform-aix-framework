package com.platform.aix.cmd.biz.cmd10001;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.base.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 心跳
 * @author Advance
 * @date 2021年09月28日 15:53
 * @since V1.0.0
 */
@Controller(Constants.CLIENT_REQ_URL+"/cmd_10001")
public class Cmd10001Handler extends CommandBaseHandler {

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd10001Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        return new APIResponse();
    }
}
