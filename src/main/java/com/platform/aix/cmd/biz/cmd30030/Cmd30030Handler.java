package com.platform.aix.cmd.biz.cmd30030;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.common.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 30030 注销
 */
@Controller(Constants.CLIENT_REQ_URL + "/cmd_30030")
public class Cmd30030Handler extends CommandBaseHandler {

    @Autowired
    private CacheService cacheService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd30030Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException {
        Cmd30030Req req = (Cmd30030Req) request;
        //切面校验方法 特殊校验，校验请求参数是否合法等
        checkBussinessFunc(Cmd30030Req.class,null); //调用校验方法统一校验
        String userid = req.getUserid();

        // todo:  判断用户是否存在
        // 系统允许相同帐号在多个终端重复登录，一个点注销后，会导致其他登录token失效而退出，合理做法增加计数器
//        cacheService.clearUserLoginCache(userid);
        return new APIResponse();
    }
}
