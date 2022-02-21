package com.platform.aix.service.task;

import com.alibaba.fastjson.JSONObject;
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

    @Scheduled(fixedRate =  10 * 1000)
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
}
