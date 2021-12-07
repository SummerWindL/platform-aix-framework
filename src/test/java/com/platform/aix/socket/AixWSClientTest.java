package com.platform.aix.socket;

import com.alibaba.fastjson.JSONObject;
import com.platform.aix.PlatformAixApplicationTests;
import com.platform.websocket.manager.PlatformWebsocketManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * websocket客户端模拟类
 * @author Advance
 * @date 2021年12月06日 15:49
 * @since V1.0.0
 */
@Slf4j
public class AixWSClientTest extends PlatformAixApplicationTests {
    private SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

    //连接数
    public static int connectNum = 0;
    //连接成功数
    public static int successNum = 0;
    //连接失败数
    public static int errorNum = 0;

    @Resource
    public PlatformWebsocketManager platformWebsocketManager;

    /**
     * 测试websocket最大连接数
     * @throws InterruptedException
     */
    @Test
    public void testConnect() throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //每次3秒打印一次连接结果
                    log.debug(dateFormat.format(System.currentTimeMillis()) +
                                    "  连接数：{}，成功数：{}，失败数：{}",
                            connectNum ,
                            successNum ,
                            errorNum);
                }
            }
        }.start();
        List<WebSocketStompClient> list = new ArrayList<>();
        log.debug("开始时间：{}", dateFormat.format(System.currentTimeMillis()));
        while (true) {
            //连接失败超过10次，停止测试
            if(errorNum > 10){
                break;
            }
            list.add(newConnect(++connectNum));
            Thread.sleep(10);
        }
    }

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

    /**
     * 建立一次连接
     * @author Advance
     * @date 2021/12/6 16:26
     */
    @Test
    public void connectTest() throws InterruptedException {
        WebSocketStompClient webSocketStompClient = newConnect(3);
        JSONObject jsonObjectTopic = new JSONObject ();
        jsonObjectTopic.put("token", "3");
        jsonObjectTopic.put("测试", "中文");
        jsonObjectTopic.put("flag", "topic");
        jsonObjectTopic.put("sendCount", 1);
        platformWebsocketManager.sendTopicToSs("3","/cmd123", jsonObjectTopic.toJSONString());
        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();
    }

    /**
     * 创建websocket连接
     * @param i
     * @return
     */
    private WebSocketStompClient newConnect(int i) {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient socketClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(socketClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler);

        String clientToken = String.valueOf(i);
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("token", clientToken);

        String url = "http://localhost:8070/demo-zx-websocket-midserver?token=" + clientToken;
        stompClient.connect(url, handshakeHeaders, connectHeaders, new TestConnectHandler());

        return stompClient;
    }

    private static synchronized void addSuccessNum() {
        successNum++;
    }

    private static synchronized void addErrorNum() {
        errorNum++;
    }

    private static class TestConnectHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            addSuccessNum();
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            addErrorNum();
        }
    }
}
