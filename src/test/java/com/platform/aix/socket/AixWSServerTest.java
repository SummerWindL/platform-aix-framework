package com.platform.aix.socket;

import com.alibaba.fastjson.JSONObject;
import com.platform.aix.PlatformAixApplicationTests;
import com.platform.websocket.manager.PlatformWebsocketManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * websocket 服务器测试类
 * @author Advance
 * @date 2021年12月06日 16:31
 * @since V1.0.0
 */
@Slf4j
public class AixWSServerTest extends PlatformAixApplicationTests {
    @Resource
    private PlatformWebsocketManager platformWebsocketManager;
    @Test
    public void broadcastTest(){
        JSONObject jsonObjectTopic = new JSONObject ();
        jsonObjectTopic.put("token", "2");
        jsonObjectTopic.put("测试", "中文");
        jsonObjectTopic.put("flag", "topic");
        jsonObjectTopic.put("sendCount", 1);
        //获取指定ws连接
        WebSocketSession wsSessionByToken = platformWebsocketManager.getWsSession("658df59ef4084c93920e12d26c1a2bf1");

        //platformWebsocketManager.sendTopicToSs("8a9372e655774682b423a9b7da95cf58","/cmd123", jsonObjectTopic.toJSONString());
        //log.info("第 {} 次发送消息：{}",1,jsonObjectTopic.toJSONString());
    }

    @Test
   public void testSendMsg(){
       JSONObject jsonObjectTopic = new JSONObject ();
       jsonObjectTopic.put("token", "2");
       jsonObjectTopic.put("测试", "中文");
       jsonObjectTopic.put("flag", "topic");
       jsonObjectTopic.put("sendCount", 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    platformWebsocketManager.sendTopicToSs("658df59ef4084c93920e12d26c1a2bf1","/cmd123",jsonObjectTopic.toJSONString());
                    log.info("发送消息：{}",jsonObjectTopic.toJSONString());
                }
            }
        }).start();
   }
}
