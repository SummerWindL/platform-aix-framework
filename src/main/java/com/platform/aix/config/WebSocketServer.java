package com.platform.aix.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Advance
 * @date 2022年07月06日 9:48
 * @since V1.0.0
 */
@Configuration
@EnableWebSocket
public class WebSocketServer implements WebSocketConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static final String ALL = "ALL";
    private static final Map<String, Map<String, List<WebSocketSession>>> SESSIONS = new ConcurrentHashMap<String, Map<String,List<WebSocketSession>>>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TextWebSocketHandler() {

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                //[{"table":"ETR_CASH_POSITION","hodingTypes":["HODING_TYPE"]},{"table":"ETR_SEC_POSITION_RULE","hodingTypes":["HODING_TYPE"]}]
                String payload = message.getPayload();
                List<TopicMessage> list = null;
                try {
                    list = JSON.parseArray(payload, TopicMessage.class);
                } catch (Exception e) {
                    session.sendMessage(new TextMessage("JSON格式错误!"));
                    return;
                }
                if (null==list || list.size()<=0) {
                    session.sendMessage(new TextMessage("没有订阅内容"));
                    return;
                }
                for (TopicMessage msg : list) {
                    put(msg, session);
                }
            }

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                logger.info("{}客户端连接头寸余额实时通知服务",session.getRemoteAddress());
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                logger.info("{}客户端关闭头寸余额实时通知服务",session.getRemoteAddress());
                for (Map<String, List<WebSocketSession>> hodingTypes : SESSIONS.values()) {
                    Collection<List<WebSocketSession>> list = hodingTypes.values();
                    for (List<WebSocketSession> temp : list) {
                        Iterator<WebSocketSession> it= temp.iterator();
                        while(it.hasNext()) {
                            WebSocketSession t=it.next();
                            if (session.getId().equals(t.getId())) {
                                it.remove();
                            }
                        }
                    }
                }
            }

            private void put(TopicMessage msg,WebSocketSession session) throws IOException {
                String table = msg.getTable();
                List<String> hodingTypes = msg.getHodingTypes();
                //PositionTableFactory factory = PositionTableFactory.valueOf(table);
//                if (null==factory) {
//                    session.sendMessage(new TextMessage("没有发布"+table+"的订阅内容"));
//                    return;
//                }
//                AbstractPositionHandler handler = factory.getHandler();
                Map<String, List<WebSocketSession>> temp = SESSIONS.get(table);
                if (temp==null) {
                    temp = new ConcurrentHashMap<String, List<WebSocketSession>>();
                    SESSIONS.put(table, temp);
                }
                if (null==hodingTypes || hodingTypes.size()<=0) {
                    List<WebSocketSession> list = temp.get(ALL);
                    if(list==null) {
                        list = new ArrayList<>();
                    }
                    list.add(session);
                    temp.put(ALL, list);
//					List<List<?>> rows = handler.listAllCacheData();
//					if (rows==null || rows.size()<=0) {
//						return;
//					}
//					String tab = factory.name();
//					for (List list : rows) {
//						list.add(0, tab);
//						session.sendMessage(new TextMessage(JSON.toJSONString(list)));
//					}
                    return;
                }
                for (String type : hodingTypes) {
                    List<WebSocketSession> list = temp.get(type);
                    if(list==null) {
                        list = new ArrayList<>();
                    }
                    list.add(session);
                    temp.put(type, list);
//					List<List<?>> rows = handler.listCacheDataByHodingType(type);
//					if (rows==null || rows.size()<=0) {
//						return;
//					}
//					String tab = factory.name();
//					for (List list : rows) {
//						list.add(0, tab);
//						session.sendMessage(new TextMessage(JSON.toJSONString(list)));
//					}
                }
            }

        }, "positionTopic").setAllowedOrigins("*");
    }

//    private void send(Map<PositionTableFactory, List<PositionFlowMap>> flows, String seqNo) throws IOException {
//        if (flows==null || flows.size()<=0) {
//            return;
//        }
//        for (Map.Entry<PositionTableFactory,List<PositionFlowMap>> entry : flows.entrySet()) {
//            PositionTableFactory factory = entry.getKey();
//            AbstractPositionHandler handler = factory.getHandler();
//            String tab = factory.name();
//            for (PositionFlowMap flow : entry.getValue()) {
//                List<List<?>> rows = handler.listCacheData(flow);
//                if (rows==null || rows.size()<=0) {
//                    continue;
//                }
//                for (List list : rows) {
//                    list.add(0, tab);
//                    send(factory.name(), flow.getHodingType(), JSON.toJSONString(list));
//                }
//                // seqNo
//                sendFlowsBySeqNo(factory.name(), seqNo);
//            }
//        }
//    }

    /**
     * 发送余额后再发送流水
     * @param tableName
     * @param seqNo
     */
    private void sendFlowsBySeqNo(String tableName, String seqNo) {
        /*if ("ETR_CASH_POSITION".equals(tableName)){
            List<PositionFlowMap> list = positionMapper.listEtrCashPositionFlowBySeqno(seqNo);
            List<Object> arr = Arrays.asList(tableName + "_FLOW", list);
            send(tableName, null, JSON.toJSONString(arr));
        }else if ("ETR_SEC_POSITION".equals(tableName)){
            List<PositionFlowMap> list = positionMapper.listEtrSecPositionFlowBySeqno(seqNo);
            List<Object> arr = Arrays.asList(tableName + "_FLOW", list);
            send(tableName, null, JSON.toJSONString(arr));
        }else if ("PTL_CASH_POSITION".equals(tableName)){
            List<PositionFlowMap> list = positionMapper.listPtlCashPositionFlowBySeqno(seqNo);
            List<Object> arr = Arrays.asList(tableName + "_FLOW", list);
            send(tableName, null, JSON.toJSONString(arr));
        }else if ("PTL_SEC_POSITION".equals(tableName)){
            List<PositionFlowMap> list = positionMapper.listPtlSecPositionFlowBySeqno(seqNo);
            List<Object> arr = Arrays.asList(tableName + "_FLOW", list);
            send(tableName, null, JSON.toJSONString(arr));
        }*/
    }

    private void send(String table,String hodingType,String dataJson)  {
        Map<String, List<WebSocketSession>> temp = SESSIONS.get(table);
        if (temp==null || temp.size()<=0) {
            return;
        }
        List<WebSocketSession> sessionList = temp.get(ALL);
        if (null!=sessionList) {
            for(WebSocketSession session:sessionList) {
                logger.info(session.getId()+":"+dataJson);
                try {
                    session.sendMessage(new TextMessage(dataJson));
                } catch (Exception e) {
                    logger.error(session.getId()+":"+dataJson);
                }
            }
            return;
        }
        if (hodingType == null){
            return;
        }
        sessionList = temp.get(hodingType);
        if (null!=sessionList) {
            for(WebSocketSession session:sessionList) {
                logger.info(session.getId()+":"+dataJson);
                try {
                    session.sendMessage(new TextMessage(dataJson));
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    logger.error(session.getId()+":"+dataJson);
                }
            }
            return;
        }
    }

    public static class TopicMessage {
        private String table;
        private List<String> hodingTypes;
        public String getTable() {
            return table;
        }
        public void setTable(String table) {
            this.table = table;
        }
        public List<String> getHodingTypes() {
            return hodingTypes;
        }
        public void setHodingTypes(List<String> hodingTypes) {
            this.hodingTypes = hodingTypes;
        }
    }
}
