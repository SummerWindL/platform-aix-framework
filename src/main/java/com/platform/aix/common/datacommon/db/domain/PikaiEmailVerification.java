package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiEmailVerificationInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class PikaiEmailVerification extends BaseEntity implements Serializable, PikaiEmailVerificationInterface, Cloneable, KeyMethodInterface<String> {
    private String verificationId;

    private String userId;

    private String email;

    private String verificationCode;

    private String status;

    private Date expireTime;

    private Date createTime;

    private Date updateTime;

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId == null ? null : verificationId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode == null ? null : verificationCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
        return this.verificationId;
    }

    @Override
    public void setId(String id) {
        this.verificationId = id;
    }
}