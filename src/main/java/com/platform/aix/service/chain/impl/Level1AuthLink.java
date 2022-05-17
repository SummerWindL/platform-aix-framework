package com.platform.aix.service.chain.impl;

import com.platform.aix.service.chain.AuthInfo;
import com.platform.aix.service.chain.AuthLink;
import com.platform.aix.service.chain.AuthService;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Advance
 * @date 2022年05月11日 16:17
 * @since V1.0.0
 */
public class Level1AuthLink extends AuthLink {

    private Date beginDate = f.parse("2022-05-11 00:00:00");
    private Date endDate = f.parse("2022-05-20 23:59:59");

    public Level1AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if(null == date){
            return new AuthInfo("0001","单号",orderId,"状态：待一级审批负责人",levelUserName);
        }
        AuthLink next = super.getNext();
        if(ObjectUtils.nullSafeHashCode(next)!=0){
            return new AuthInfo("0000","单号",orderId,"状态：一级审批负责人完成",levelUserName);
        }
        if (authDate.before(beginDate) || authDate.after(endDate)) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：一级审批负责人完成", " 时间：", f.format(date), " 审批人：", levelUserName);
        }
        return next.doAuth(uId, orderId, authDate);
    }
}
