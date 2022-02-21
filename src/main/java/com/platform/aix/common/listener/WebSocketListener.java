package com.platform.aix.common.listener;

import com.alibaba.fastjson.JSONObject;
import com.platform.websocket.manager.PlatformWebsocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用platform-websocket暴露的【PlatformWebsocketManager】实现websocket管道
 * @author Advance
 * @date 2021年11月19日 17:31
 * @since V1.0.0
 */
@Component
@Slf4j
public class WebSocketListener extends StartupConfiguration {

    @Resource
    private PlatformWebsocketManager platformWebsocketManager;
    @Override
    public void afterStartup(ApplicationContext applicationContext) {
        //initWebsocket();
        //模拟client建立连接
        newClient();
    }

    private WebSocketStompClient newClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient socketClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(socketClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler);

        String clientToken = "ssid123";
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("token", clientToken);

        String url = "http://localhost:8069/demo-zx-websocket-midserver?token=" + clientToken;
        stompClient.connect(url, handshakeHeaders, connectHeaders, new TestConnectHandler());

        return stompClient;
    }

    private static class TestConnectHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            //addSuccessNum();
            log.info("建立连接成功...");
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            //addErrorNum();
            log.error("建立连接失败...");
        }
    }

    private void initWebsocket() {
        log.info("启动wehsocket监听......");
        //SpringApplication.run(PlatformWebsocketApplication.class);
        //	模拟给指定 ssid 的患者发送消息	Topic
        JSONObject jsonObjectTopic = new JSONObject ();
        jsonObjectTopic.put("token", "ssid123");
        jsonObjectTopic.put("测试", "中文");
        jsonObjectTopic.put("flag", "topic");
        jsonObjectTopic.put("sendCount", 1);
        platformWebsocketManager.sendTopicToSs("ssid123","/cmd123", jsonObjectTopic.toJSONString());
        log.info("第 {} 次发送消息：{}",1,jsonObjectTopic.toJSONString());
    }
}
