package com.platform.aix.cmd.biz.cmd30010;

import com.cluster.platform.redis.model.TUserLoginCache;
import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.cmd.bean.response.PrjInfo;
import com.platform.aix.cmd.bean.response.UserInfo;
import com.platform.aix.common.annotation.DisableCheckAuthHeader;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.request.RequestAuthHeader;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.config.ApisPorperties;
import com.platform.aix.service.common.CacheService;
import com.platform.aix.service.user.account.UserAccountService;
import com.platform.aix.service.user.account.bean.TUserAccount;
import com.platform.aix.service.user.menu.UserAccountFunctionMenuService;
import com.platform.aix.service.user.prj.UserAccountPrjService;
import com.platform.aix.service.user.prj.bean.TUserAccountPrj;
import com.platform.common.util.UUIDUtils;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

/**
 * 30010 登录
 */
@DisableCheckAuthHeader
@Controller(Constants.CLIENT_REQ_URL + "/cmd_30010")
public class Cmd30010Handler extends CommandBaseHandler {

    @Autowired
    private ApisPorperties apisPorperties;

    @Autowired
    UserAccountFunctionMenuService userAccountFunctionMenuService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountPrjService userAccountPrjTiltleService;


    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd30010Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException {
        Cmd30010Req req = (Cmd30010Req) request;

        String acc = req.getAcc();
        String pwd = req.getPwd();

        // 1.校验密码
        if (StringUtils.isEmpty(pwd)) {
            throw new BIZException(ResponseResult.BIZ_ERROR_RSA_DECODE);
        }

        // 查询登录帐号是否存在
        TUserAccount userAccount = userAccountService.getDxUserAccount(acc);

        // 如果帐号存在，验证帐号密码
        userAccountService.checkUserPwd(acc, pwd);
        // 公卫帐号首次登录成功后需要再次查询用户信息
        userAccount = userAccountService.getDxUserAccount(acc);
        if (null == userAccount) {
            throw new BIZException(ResponseResult.BIZ_ERROR_USER_PWD_WRONG);
        }

        String userid = userAccount.getUserid();
        String username = userAccount.getName();

        //查询用户所属划区
//        MbHospDoctorRgngrpFilter filter = new MbHospDoctorRgngrpFilter();
//        filter.setUserid(userid);
//        filter.setDoctoruserid(userid);
//        filter.setRegionlevel(-1);//等级条件为空
//        filter.setCountflag(3);
//        filter.setPage(1);
//        filter.setPagesize(Integer.MAX_VALUE);
//        Page<MbHospDoctorRgngrp> mbHospDoctorRgngrps = hospDoctorRgngrpService.queryHospDoctorRgngrp(filter);
//        List<MbHospDoctorRgngrp> doctorRgngrpList = mbHospDoctorRgngrps.getContent();

        // 4.写用户帐号信息
        // 用户令牌token，登录时更新token，后续请求若token未过期，更新token过期时间。
        //获取当前用户是否存在未过期登录信息
        TUserLoginCache loginCache = cacheService.getUserLoginCache(userid);
        String token = loginCache.getToken();
        if (StringUtils.isEmpty(token)) {
            token = UUIDUtils.getUUID();
        }

        // 填充鉴权头
        RequestAuthHeader authHeader = new RequestAuthHeader();
        String cacheKey = cacheService.getUserLoginCacheKey(userid);
        authHeader.setId(cacheKey);
        authHeader.setToken(token);

        // 查询帐号对应的权限菜单
        JSONArray functionMenu = userAccountFunctionMenuService.getUserAccountFunctionMenuList(acc);

        // 查询帐号对应的项目信息
        TUserAccountPrj[] tUserAccountPrjs = userAccountPrjTiltleService.queryUserAccountPrj(userid);
        PrjInfo prjinfo = null;
        if (null != tUserAccountPrjs && tUserAccountPrjs.length > 0) {
            prjinfo = new PrjInfo();
            TUserAccountPrj tUserAccountPrj = tUserAccountPrjs[0];
            prjinfo.setPrjtitle(tUserAccountPrj.getTitlename());
            prjinfo.setPrjlogo(tUserAccountPrj.getTitlelogo());
        }

        // 6.构造响应数据
        Cmd30010Response data = new Cmd30010Response();
        data.setAuth(authHeader);
        data.setUserid(userid);

        UserInfo userinfo = new UserInfo();
        BeanUtils.copyProperties(userAccount, userinfo);
        userinfo.setUsername(username);
        data.setUserinfo(userinfo);
        data.setPrjinfo(prjinfo);
        //获取区划通过单独接口查询
        //data.setMbHospDoctorRgngrps(doctorRgngrpList);
        // 获取菜单单独一个接口合理，登录进去后页面刷新需要重新加载菜单
        //data.setFunctionMenu(functionMenu);

        data.setTokenexpire(apisPorperties.getTokenExpire());

        // 缓存处理
        // 缓存帐号信息
        loginCache.setAcc(acc);
        loginCache.setToken(token);

        // 缓存帐号菜单权限
        loginCache.setMenu(functionMenu);

        // 是否需要缓存用户权限？如果业务场景是立即生效，就不需要缓存，否则需要缓存权限。

        // 更新用户登录信息到缓存
        cacheService.setUserLoginCache(userid, loginCache);

        return new APIResponse(data);
    }
}
