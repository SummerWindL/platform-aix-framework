package com.platform.aix.service.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 责任链审核抽象类
 * @author Advance
 * @date 2022年05月11日 16:11
 * @since V1.0.0
 */
public abstract class AuthLink {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected String levelUserId;
    protected String levelUserName;
    protected AuthLink next; //链路

    public AuthLink(String levelUserId, String levelUserName) {
        this.levelUserId = levelUserId;
        this.levelUserName = levelUserName;
    }

    public AuthLink getNext() {
        return next;
    }

    public AuthLink appendNext(AuthLink next){
        this.next = next;
        return this; //返回实现类对象
    }
    public abstract AuthInfo doAuth(String uId, String orderId, Date authDate);
}
