package com.platform.aix.service.task;

import com.alibaba.fastjson.JSONObject;
import com.platform.aix.common.spring.AppService;
import com.platform.aix.module.cache.EntrustIdManager;
import com.platform.websocket.manager.PlatformWebsocketManager;
import com.platform.websocket.service.IWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * websocket测试类
 * @author Advance
 * @date 2021年12月06日 15:23
 * @since V1.0.0
 */
@Component
@Slf4j
public class FixContainerTask implements IWebSocketService {
    private long sendCount = 0 ;     // 自启动以来，累计总发送topic的数量

    @Resource
    private PlatformWebsocketManager platformWebsocketManager;

    /**
     * 测试websocket 管道服务
     */
    //@Scheduled(fixedRate =  10 * 1000)
    public void sendSocket(){
        this.sendCount++;
        //	模拟给指定 ssid 的患者发送消息	Topic
        JSONObject jsonObjectTopic = new JSONObject ();
        jsonObjectTopic.put("token", "ssid123");
        jsonObjectTopic.put("测试", "中文");
        jsonObjectTopic.put("flag", "topic");
        jsonObjectTopic.put("sendCount", sendCount);
        platformWebsocketManager.sendTopicToSs("ssid123","/cmd123", jsonObjectTopic.toJSONString());
        log.info("第 {} 次发送消息：{}",this.sendCount,jsonObjectTopic.toJSONString());
    }

    @Override
    public void invokeCloseSocket(String... token) {
        log.info("websocket关闭！");

    }

    @Scheduled(cron = "${agent.cache.clean:0 0 16 ? * MON-FRI}")
    public void cleanEntrustCache() {
        EntrustIdManager manager = AppService.getApplicationContext().getBean(EntrustIdManager.class);
        try {
            manager.clearCache();
        } catch (Exception e) {
            log.error("cleanEntrustCache error: {}", e.getMessage(), e);
        }
    }
}
