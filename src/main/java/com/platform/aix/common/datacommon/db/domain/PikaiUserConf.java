package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiUserConfInterface;
import com.platform.aix.service.pikai.PikaiUserInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class PikaiUserConf extends BaseEntity implements Serializable, PikaiUserConfInterface, Cloneable, KeyMethodInterface<Integer> {
    private Integer id;

    private String userId;

    private String confItemCode;

    private String confItemName;

    private Object confItemValue;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getConfItemCode() {
        return confItemCode;
    }

    public void setConfItemCode(String confItemCode) {
        this.confItemCode = confItemCode == null ? null : confItemCode.trim();
    }

    public String getConfItemName() {
        return confItemName;
    }

    public void setConfItemName(String confItemName) {
        this.confItemName = confItemName == null ? null : confItemName.trim();
    }

    public Object getConfItemValue() {
        return confItemValue;
    }

    public void setConfItemValue(Object confItemValue) {
        this.confItemValue = confItemValue;
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
}