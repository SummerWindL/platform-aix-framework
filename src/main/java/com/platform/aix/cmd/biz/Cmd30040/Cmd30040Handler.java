package com.platform.aix.cmd.biz.Cmd30040;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.common.CacheService;
import com.platform.aix.service.user.account.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 30040 修改密码
 */
@Controller(Constants.CLIENT_REQ_URL + "/cmd_30040")
public class Cmd30040Handler extends CommandBaseHandler {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CacheService cacheService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd30040Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException {
        Cmd30040Req req = (Cmd30040Req) request;
        String userid = req.getUserid();
        String acc = req.getAcc();
        String oldpwd = req.getOldpwd();
        String newpwd = req.getNewpwd();

        /**
         * 1.验证帐号+旧密码
         */
        userAccountService.checkUserPwd(acc, oldpwd);

        /**
         * 2.更新帐号信息
         */
        userAccountService.modUserAccountPwd(userid, acc, oldpwd, newpwd);

        /**
         * 3.置用户登录TOKEN过期
         */
        cacheService.clearUserLoginCache(userid);

        return new APIResponse();
    }
}
