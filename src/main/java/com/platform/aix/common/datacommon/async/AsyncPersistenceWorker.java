package com.platform.aix.common.datacommon.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Advance
 */
@Component
public class AsyncPersistenceWorker {
    private static final Logger logger = LoggerFactory.getLogger(AsyncPersistenceWorker.class);
    @Autowired
    @Qualifier("asyncPersistenceHandler")
    private AsyncPersistenceHandler persistenceHandler;

    @Value("${async.buffer.size.db:131072}")
    private int bufferSize;
    @Value("${async.buffer.size.drain:200}")
    private int drainSize;

    private BlockingQueue<AsyncPersistenceBean> msgQueue;

    public static AtomicInteger productOffset = new AtomicInteger(0);

    public static AtomicInteger consumerOffset = new AtomicInteger(0);

    public static AtomicInteger exceptionOffset = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        msgQueue = new LinkedBlockingQueue<>(bufferSize);
        Executors.newSingleThreadExecutor().submit(() -> {
            List<AsyncPersistenceBean> asyncPersistenceBeans = new ArrayList<>();
            while (true) {
                try {
                    AsyncPersistenceBean bean = msgQueue.poll(1, TimeUnit.MILLISECONDS);
                    if (Objects.nonNull(bean)) {
                        asyncPersistenceBeans.add(bean);
                        msgQueue.drainTo(asyncPersistenceBeans, drainSize);
                        persistenceHandler.doTransaction(asyncPersistenceBeans);
                        asyncPersistenceBeans.clear();
                    }
                } catch (Exception e) {
                    logger.error("msgQueue exception:{}", e);
                }
            }
        });
    }

    public void async(IAsync async) {
        AsyncPersistenceBean msgBean = new AsyncPersistenceBean();
        msgBean.setAsync(async);
        boolean offer = msgQueue.offer(msgBean);
        if (!offer) {
            logger.error("publish async failed");
            return;
        }
        productOffset.incrementAndGet();
    }
}
