package com.platform.aix.cmd.biz.cmd10000;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 *
 * 登录
 * @author Advance
 * @date 2021年09月24日 8:53
 * @since V1.0.0
 */
@Controller(Constants.CLIENT_REQ_URL+"/cmd_10000")
public class Cmd10000Handler extends CommandBaseHandler {
    /**
     * 获取请求
     * @author Advance
     * @date 2021/9/24 8:57
     * @return java.lang.Class<? extends com.platform.aix.cmd.bean.request.BaseRequest>
     */
    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return null;
    }

    /**
     * 执行方法
     * @author Advance
     * @date 2021/9/24 8:57
     * @param request
     * @return com.platform.aix.common.response.APIResponse
     */
    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        return null;
    }
}
