package com.platform.aix.service.chain.impl;

import com.platform.aix.service.chain.AuthInfo;
import com.platform.aix.service.chain.AuthLink;
import com.platform.aix.service.chain.AuthService;

import java.util.Date;

/**
 * @author Advance
 * @date 2022年05月11日 16:41
 * @since V1.0.0
 */
public class Level3AuthLink extends AuthLink {
    public Level3AuthLink(String levelUserId, String levelUserName) {
        super(levelUserId, levelUserName);
    }

    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if (null == date) {
            return new AuthInfo("0001", "单号：", orderId, " 状态：待三级审批负责人 ", levelUserName);
        }
        AuthLink next = super.getNext();
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：三级审批完成负责人", " 时间：", f.format(date), " 审批人：", levelUserName);
        }

        return next.doAuth(uId, orderId, authDate);
    }
}
