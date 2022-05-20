package com.platform.aix.service.server.agent;

import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.spring.AppService;
import com.platform.aix.service.processor.msg.MsgReceiverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 最后加载
 * @author Advance
 * @date 2022年05月18日 10:33
 * @since V1.0.0
 */
@Component
@Order(99)
public class TcpServer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);
    @Resource
    private ApplicationContext ctx;
    @Autowired
    private MsgReceiverManager msgReceiverManager;
    private volatile boolean startFlag = true;

    @PostConstruct
    public void start() {
        AppService.setApplicationContextStatic(ctx);
    }
    @Override
    @Lazy
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (startFlag) {
                startFlag = false;
                msgReceiverManager.init();
                logger.error("建立TCP连接成功");
            }
        } catch (Exception e) {
            logger.error("建立TCP连接失败： {}", e);
        }
    }

}
