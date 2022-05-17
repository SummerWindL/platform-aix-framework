package com.platform.aix.service.server.agent;


import com.platform.aix.cmd.bean.Account;
import com.platform.aix.common.constants.AccountLoginStatus;

import java.util.Map;

public interface TradeAgent extends IAgentCommand {
    Map<String, Account> getAccountMap();

    String getTerminalName();

    default void setStatus(String accountId, AccountLoginStatus status) {

    }

    /**
     * 删除账号
     *
     * @param accountId 账号id
     */
    default void removeAccount(String accountId) {

    }

    /**
     * 报价查询、成交查询
     */
    default void queryNextTrade() {

    }
}
