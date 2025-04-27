package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiLoginLogInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class PikaiLoginLog extends BaseEntity implements Serializable, PikaiLoginLogInterface, Cloneable, KeyMethodInterface<String> {
    private String loginId;

    private String accountId;

    private String loginIp;

    private Date loginTime;

    private String loginType;

    private String loginDevice;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType == null ? null : loginType.trim();
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice == null ? null : loginDevice.trim();
    }

    @Override
    public String getId() {
        return this.loginId;
    }

    @Override
    public void setId(String id) {
        this.loginId = id;
    }
}