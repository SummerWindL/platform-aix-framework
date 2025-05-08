package com.platform.aix.controller.pikai.common.request;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月08日 17:03
 */
public class PikaiUserConfReq {
    private String confItemCode;
    private String confItemName;
    private Object confItemValue;
    private String userId;

    public String getConfItemCode() {
        return confItemCode;
    }

    public void setConfItemCode(String confItemCode) {
        this.confItemCode = confItemCode;
    }

    public String getConfItemName() {
        return confItemName;
    }

    public void setConfItemName(String confItemName) {
        this.confItemName = confItemName;
    }

    public Object getConfItemValue() {
        return confItemValue;
    }

    public void setConfItemValue(Object confItemValue) {
        this.confItemValue = confItemValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
