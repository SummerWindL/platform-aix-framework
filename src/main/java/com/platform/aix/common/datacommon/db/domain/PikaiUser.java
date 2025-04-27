package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiUserInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class PikaiUser extends BaseEntity implements Serializable, PikaiUserInterface, Cloneable, KeyMethodInterface<String> {
    private String userId;

    private String accountId;

    private String userName;

    private String salt;

    private String password;

    private String encrypType;

    private Object userInfo;

    private String accountStatus;

    private Date createTime;

    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEncrypType() {
        return encrypType;
    }

    public void setEncrypType(String encrypType) {
        this.encrypType = encrypType == null ? null : encrypType.trim();
    }

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getId() {
        return this.userId;
    }

    @Override
    public void setId(String id) {
        this.userId= id;
    }
}