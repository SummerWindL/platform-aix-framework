//package com.platform.aix.module.message;
//
//import com.alibaba.fastjson.JSON;
//import com.platform.aix.common.constants.RedisKeyEnum;
//import com.platform.aix.common.datacommon.db.domain.User;
//import com.platform.aix.service.processor.HospitalElectronicPaperProcessor;
//import com.platform.common.util.UUIDUtils;
//import com.platform.core.rabbitmq.annotation.MessageSender;
//import com.platform.core.util.IDGenerator;
//import com.platform.rabbitmq.entity.DefaultCorrelationData;
//import com.platform.rabbitmq.entity.SimpleRabbitMessage;
//import com.platform.rabbitmq.sender.RabbitReceiveService;
//import com.platform.rabbitmq.sender.RabbitSender;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author Advance
// * @date 2022年07月18日 17:05
// * @since V1.0.0
// */
//@RestController
//@Slf4j
//public class RabbitMessageController  implements ChannelAwareMessageListener {
//    private static final long SECOND = 1000;
//
//    @Autowired
//    RabbitSender sender;
//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//    public Object sendAndReceive(String exchange, String routineKey, Object content) {
//        Object obj = rabbitTemplate.convertSendAndReceive(exchange, routineKey, content);
//        return obj;
//    }
//    @GetMapping("/send3")
//    @ResponseBody
//    public void sendMessage3(){
//        SimpleRabbitMessage defaultRabbitMessage = new SimpleRabbitMessage();
//        String msgId = IDGenerator.generate();
//        User user = new User();
//        user.setId(UUIDUtils.getUUID());
//        user.setUsername("Send3");
//        String jsonInfo = JSON.toJSONString(user);
//        defaultRabbitMessage.setId(UUIDUtils.getUUID());
//        defaultRabbitMessage.setMessage(jsonInfo);
//        DefaultCorrelationData defaultCorrelationData = new DefaultCorrelationData(this.getClass().getName(), "yitihua.test2.dispatch",defaultRabbitMessage);
//        Object a =  sendAndReceive("exchange.topic.test2","yitihua.test2.dispatch",defaultCorrelationData);
//        System.out.println("处理结果："+a.toString());
//    }
//
//    @MessageSender(exchange = "exchange.topic.test", routingKey = "yitihua.test.dispatch")
//    @GetMapping("/send")
//    @ResponseBody
//    //@Scheduled(fixedRate = 3 * SECOND)
//    public SimpleRabbitMessage sendMessage(){
//        String msg = "121212";
//        SimpleRabbitMessage defaultRabbitMessage = new SimpleRabbitMessage();
//        String msgId = IDGenerator.generate();
//        User user = new User();
//        user.setId(msgId);
//        user.setUsername("Advance");
//        String dtoStr = JSON.toJSONString(user);
//
//        Map<String,Object> info = new HashMap<>();
//        Map<String,Map<String,Object>> infoList = new HashMap<>();
//        info.put(msgId, dtoStr);
//        infoList.put(RedisKeyEnum.MQ指令信息推送.key(), info);
//
//        log.info("==========推送银行间回复消息数据到mq============ ");
//        log.info("pushBankUpMsgToMQ msgId:"+ msgId);
//        log.info("pushBankUpMsgToMQ message: "+ dtoStr);
//
//        defaultRabbitMessage.setId(msgId);
//        defaultRabbitMessage.setMessage(dtoStr);
//        //Object o = sender.send();
//        return defaultRabbitMessage;
//    }
//
//    @MessageSender(exchange = "exchange.topic.yitihua.interf", routingKey = "yitihua.interf.dispatch")
//    @GetMapping("/send2")
//    @ResponseBody
//    public SimpleRabbitMessage sendMessage2(){
//        String msg = "121212";
//        SimpleRabbitMessage defaultRabbitMessage = new SimpleRabbitMessage();
//        String msgId = IDGenerator.generate();
//        User user = new User();
//        user.setCreateUser(msgId);
//        HashMap<String,Object> map = new HashMap<>();
//        map.put("cmdNo","cmd_10000");
//        map.put("cmdOperatorType","insert");
//        map.put("payload",new ArrayList<>().add(user));
//        String dtoStr = JSON.toJSONString(map);
//
//        Map<String,Object> info = new HashMap<>();
//        Map<String,Map<String,Object>> infoList = new HashMap<>();
//        info.put(msgId, dtoStr);
//        infoList.put(RedisKeyEnum.MQ指令信息推送.key(), info);
//        log.info("==========推送银行间回复消息数据到mq============ ");
//        log.info("pushBankUpMsgToMQ msgId:"+ msgId);
//        log.info("pushBankUpMsgToMQ message: "+ dtoStr);
//
//        defaultRabbitMessage.setId(msgId);
//        defaultRabbitMessage.setMessage(dtoStr);
//        return defaultRabbitMessage;
//    }
//
//    @Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        System.out.println("收到消息："+message);
//    }
//}
