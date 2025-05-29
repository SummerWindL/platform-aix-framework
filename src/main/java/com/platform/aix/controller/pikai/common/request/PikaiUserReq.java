package com.platform.aix.controller.pikai.common.request;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月20日 16:48
 */
public class PikaiUserReq {
    private String userName;
    private String userId;
    /**
     * jsonb格式
     */
    private Object userInfo;

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
