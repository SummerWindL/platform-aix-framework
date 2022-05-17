package com.platform.aix.cmd.bean;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable, KeyMethodInterface<String> {
    private static final long serialVersionUID = -5054337370709351813L;
    /**
     * 网点账号
     */
    private String id;


    /**
     * 账户账号
     */
    private String financeAccount;

    /**
     * 账户姓名
     */
    private String accountName;

    /**
     * 账户姓名
     */
    private String currencyType;

    /**
     * 类型
     */
    private String accountType;

    private String brokerId;

    private String brokerName;

    private String pwd;

    private String tradePwd;

    private Short usingStatus;

    private String riskParameter;

    private Date createTime;

    private Date updateTime;

    private String agentType;

    private int count;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFinanceAccount() {
        return financeAccount;
    }

    public void setFinanceAccount(String financeAccount) {
        this.financeAccount = financeAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public Short getUsingStatus() {
        return usingStatus;
    }

    public void setUsingStatus(Short usingStatus) {
        this.usingStatus = usingStatus;
    }

    public String getRiskParameter() {
        return riskParameter;
    }

    public void setRiskParameter(String riskParameter) {
        this.riskParameter = riskParameter;
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

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}