package com.platform.aix.service.processor.msg;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.platform.aix.service.server.agent.TradeAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
//@Scope("prototype")
public class TradeMsgReceiver {

    private static final Logger logger = LoggerFactory.getLogger(TradeMsgReceiver.class);
    @Autowired
    private MsgBeanHandler msgBeanHandler;

    @Value("${async.buffer.size.trade: 65536}")
    private int bufferSize;

    @Autowired
    private MsgBeanFactory eventFactory;

    private Disruptor<MsgBean> disruptor;

    private RingBuffer<MsgBean> ringBuffer;


    public static AtomicInteger productOffset = new AtomicInteger(0);
    public static AtomicInteger consumerOffset = new AtomicInteger(0);
    public static AtomicInteger exceptionOffset = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        String threadName = "msg consumer" + this.hashCode();
        disruptor = new Disruptor<>(eventFactory, bufferSize, (Runnable r) -> new Thread(r, threadName),
                ProducerType.MULTI, new SleepingWaitStrategy());
        disruptor.handleEventsWith(msgBeanHandler);
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
        logger.info("disruptor start {}",threadName);
    }

    private void publish(int msgType, Object msg) {
        publish(msgType, msg, null);
    }

    private void publish(int msgType, Object msg, TradeAgent agent) {
        long seq = ringBuffer.next();
        try {
            MsgBean msgBean = ringBuffer.get(seq);
            msgBean.setMsgType(msgType);
            msgBean.setMsg(msg);
            msgBean.setTradeAgent(agent);
        } catch (Throwable e) {
            logger.error("publish exception:{}", e);
        } finally {
            ringBuffer.publish(seq);
            productOffset.incrementAndGet();
        }
    }

    public void addMsg(int msgType, Object msg, TradeAgent agent) {
        this.publish(msgType, msg, agent);
    }

    public void addMsg(int msgType, Object msg) {
        this.publish(msgType, msg);
    }
}

