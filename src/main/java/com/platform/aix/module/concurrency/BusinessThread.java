package com.platform.aix.module.concurrency;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Advance
 * @date 2022年05月24日 23:14
 * @since V1.0.0
 */
@Component
@Scope("prototype")//spring 多例
public class BusinessThread implements Runnable{

    private String acceptStr;

    public BusinessThread(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    public String getAcceptStr() {
        return acceptStr;
    }

    public void setAcceptStr(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    @Override
    public void run() {
        //业务操作
        System.out.println("多线程已经处理订单插入系统，订单号："+acceptStr);

        //线程阻塞
        /*try {
            Thread.sleep(1000);
            System.out.println("多线程已经处理订单插入系统，订单号："+acceptStr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
