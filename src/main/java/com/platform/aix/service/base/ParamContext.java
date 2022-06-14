package com.platform.aix.service.base;

import cn.hutool.db.sql.Order;
import com.platform.aix.cmd.bean.Trade;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Advance
 * @date 2022年06月01日 15:45
 * @since V1.0.0
 */
public class ParamContext {
    private Map<String, Order> orderMap = new HashMap<>();
    private Trade tradeRecord = null;

    public void clear(){
        orderMap.clear();
        tradeRecord = null;
    }

    public Map<String, Order> getOrderMap() {
        return orderMap;
    }
    public void addOrder(Order order) {
        if (Objects.nonNull(order)) orderMap.put(order.getField(), order);
    }

    public void setOrderMap(Map<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public Trade getTradeRecord() {
        return tradeRecord;
    }

    public void setTradeRecord(Trade tradeRecord) {
        this.tradeRecord = tradeRecord;
    }
}
