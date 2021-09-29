package com.platform.aix.cmd.biz.cmd31020;

import com.cluster.platform.redis.model.TUserLoginCache;
import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.common.CacheService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 30020 获取功能菜单
 */
@Controller(Constants.CLIENT_REQ_URL + "/cmd_31020")
public class Cmd31020Handler extends CommandBaseHandler {

    @Autowired
    private CacheService cacheService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd31020Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException {
        Cmd31020Req req = (Cmd31020Req) request;

        String userid = req.getUserid();

        TUserLoginCache loginCache = cacheService.getUserLoginCache(userid);

        // 正常情况不会为空
        if (loginCache == null) {
            throw new BIZException(ResponseResult.BIZ_ERROR_USER_NOT_EXIST);
        }

        JSONArray menuArray = loginCache.getMenu();

        return new APIResponse(menuArray);
    }
}
