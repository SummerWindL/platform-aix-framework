package com.platform.aix.controller.pikai.common.request;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月20日 17:21
 */
public class PikaiPasswordReq {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
