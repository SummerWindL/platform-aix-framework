//package com.platform.aix.module.message;
//
//import com.alibaba.fastjson.JSONObject;
//import com.platform.core.rabbitmq.entity.IRabbitMessage;
//import com.platform.rabbitmq.entity.SimpleRabbitMessage;
//import com.platform.rabbitmq.util.RabbitUtils;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
///**
// * @author Advance
// * @date 2022年07月18日 17:21
// * @since V1.0.0
// */
//@Slf4j
//@Lazy(false)
//@Service
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//public class ConsumerListener {
//
//    //@RabbitListener(queues = {"queue.bankup.trade","queue.interf.execute"})
//    @RabbitListener(queues = "queue.interf.execute")
//    public String process(SimpleRabbitMessage context, Channel channel, Message message) throws IOException {
//        System.err.println("dlx queue");
//        //RabbitUtils.ack(channel, message);
//        log.info(JSONObject.toJSONString(context.getMessage()));
//        return "接收成功";
//    }
//
//    /*@Override
//    public void onMessage(Message message, Channel channel) throws Exception {
//        long deliverTag = message.getMessageProperties().getDeliveryTag();
//        RabbitUtils.ack(channel, message);
//        System.out.println("MyAckReceiver");
//        try {
//            String msg = message.toString();
//            System.out.println(msg);
//            System.out.println("消息的内容为："+message.getMessageProperties());
//            System.out.println("消息的内容为:"+message.getBody());
//            System.out.println("消息来自队列："+message.getMessageProperties().getConsumerQueue());
//            channel.basicAck(deliverTag,true);
//        } catch (Exception e){
//            System.out.println("发生了异常");
//            channel.basicReject(deliverTag,false);
//            e.printStackTrace();
//        }
//
//    }*/
//}
