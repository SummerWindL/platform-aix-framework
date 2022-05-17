package com.platform.aix.common.constants;

/**
 * @author Advance
 * @date 2022年05月17日 15:41
 * @since V1.0.0
 */
public enum AccountLoginStatus {
    NOT_LOGGED(0, "未登录"),
    LOGGED(1, "账户登录"),
    ENTRUST_REISSUING(2, "委托对比中"),
    ENTRUST_REISSUING_SUCCESS(3, "委托对比成功"),
    TRADE_REISSUING(4, "成交对比中"),
    TRADE_REISSUING_SUCCESS(5, "成交对比成功"),
    TRADING(6, "交易中");

    AccountLoginStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public final int value;
    public final String msg;

    public int value() {
        return this.value;
    }

    public String msg() {
        return this.msg;
    }
}
