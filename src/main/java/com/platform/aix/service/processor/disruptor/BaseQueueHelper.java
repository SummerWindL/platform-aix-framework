package com.platform.aix.service.processor.disruptor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.platform.aix.service.processor.msg.MsgBean;
import com.platform.aix.service.server.agent.TradeAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Advance
 * @date 2022年05月17日 16:03
 * @since V1.0.0
 */
public abstract class BaseQueueHelper<D, E extends ValueWrapper<D>, H extends WorkHandler<E>> {
    private static final Logger logger = LoggerFactory.getLogger(BaseQueueHelper.class);

    public static AtomicInteger productOffset = new AtomicInteger(0);
    /**
     * 记录所有的队列，系统退出时统一清理资源
     */
    private static List<BaseQueueHelper> queueHelperList = new ArrayList<BaseQueueHelper>();
    /**
     * Disruptor 对象
     */
    private Disruptor<E> disruptor;
    /**
     * RingBuffer
     */
    private RingBuffer<E> ringBuffer;

    /**
     * Disruptor 对象
     */
    private Disruptor<MsgBean> disruptorMsg;
    /**
     * RingBuffer
     */
    private RingBuffer<MsgBean> ringBufferMsg;
    /**
     * initQueue
     */
    private List<D> initQueue = new ArrayList<D>();

    /**
     * 队列大小
     *
     * @return 队列长度，必须是2的幂
     */
    protected abstract int getQueueSize();

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    protected abstract EventFactory<E> eventFactory();

    /**
     * 事件消费者
     *
     * @return WorkHandler[]
     */
    protected abstract WorkHandler[] getHandler();

    /**
     * 初始化
     */
    public void init() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DisruptorThreadPool").build();
        disruptor = new Disruptor<E>((com.lmax.disruptor.EventFactory<E>) eventFactory(),
                getQueueSize(), namedThreadFactory, ProducerType.SINGLE, getStrategy());
        disruptor.setDefaultExceptionHandler(new MyHandlerException());
        disruptor.handleEventsWithWorkerPool(getHandler());
        ringBuffer = disruptor.start();

        //初始化数据发布
        for (D data : initQueue) {
            ringBuffer.publishEvent(new EventTranslatorOneArg<E, D>() {
                @Override
                public void translateTo(E event, long sequence, D data) {
                    event.setValue(data);
                }
            }, data);
        }

        //加入资源清理钩子
        synchronized (queueHelperList) {
            if (queueHelperList.isEmpty()) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        for (BaseQueueHelper baseQueueHelper : queueHelperList) {
                            baseQueueHelper.shutdown();
                        }
                    }
                });
            }
            queueHelperList.add(this);
        }
    }

    /**
     * 如果要改变线程执行优先级，override此策略. YieldingWaitStrategy会提高响应并在闲时占用70%以上CPU，
     * 慎用SleepingWaitStrategy会降低响应更减少CPU占用，用于日志等场景.
     *
     * @return WaitStrategy
     */
    protected abstract WaitStrategy getStrategy();

    /**
     * 插入队列消息，支持在对象init前插入队列，则在队列建立时立即发布到队列处理.
     */
    public synchronized void publishEvent(D data) {
        if (ringBuffer == null) {
            initQueue.add(data);
            return;
        }
        ringBuffer.publishEvent(new EventTranslatorOneArg<E, D>() {
            @Override
            public void translateTo(E event, long sequence, D data) {
                event.setValue(data);
            }
        }, data);
    }

    public synchronized  <T> T publishEvent(D data,T handler) {
        if (ringBuffer == null) {
            initQueue.add(data);
            return null;
        }
        long seq = ringBuffer.next();
        try {
            E e = ringBuffer.get(seq);
        }catch (Throwable e){
            logger.error("publish exception:{}", e);
        }finally {
            ringBufferMsg.publish(seq);
            productOffset.incrementAndGet();
        }
        return null;
    }

    private void publish(int msgType, Object msg) {
        publish(msgType, msg, null);
    }

    private void publish(int msgType, Object msg, TradeAgent agent) {
        long seq = ringBufferMsg.next();
        try {
            MsgBean msgBean = ringBufferMsg.get(seq);
            msgBean.setMsgType(msgType);
            msgBean.setMsg(msg);
            msgBean.setTradeAgent(agent);
        } catch (Throwable e) {
            logger.error("publish exception:{}", e);
        } finally {
            ringBufferMsg.publish(seq);
            productOffset.incrementAndGet();
        }
    }

    /**
     * 关闭队列
     */
    public void shutdown() {
        disruptor.shutdown();
    }
}
