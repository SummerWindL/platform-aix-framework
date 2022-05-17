package com.platform.aix.service.processor.msg;

import com.platform.aix.service.server.agent.TradeAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MsgBean implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(MsgBean.class);

    private int msgType;
    private long msgKey;
    private Object msg;
    private TradeAgent tradeAgent;    // 下单agent，处理下单时不用重新查找

    public MsgBean() {

    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(long msgKey) {
        this.msgKey = msgKey;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public TradeAgent getTradeAgent() {
        return tradeAgent;
    }

    public void setTradeAgent(TradeAgent tradeAgent) {
        this.tradeAgent = tradeAgent;
    }

    public MsgBean clone() {
        try {
            return (MsgBean) super.clone();
        } catch (Exception e) {
            logger.warn("clone error.{}", e);
            return new MsgBean();
        }
    }
}
