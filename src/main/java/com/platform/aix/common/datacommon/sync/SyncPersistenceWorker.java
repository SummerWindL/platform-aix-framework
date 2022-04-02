package com.platform.aix.common.datacommon.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SyncPersistenceWorker {
    private static final Logger logger = LoggerFactory.getLogger(SyncPersistenceWorker.class);
    @Autowired
    private SyncPersistenceHandler syncPersistenceHandler;

    @Value("${async.buffer.size.db:131072}")
    private int bufferSize;

    private BlockingQueue<SyncPersistenceBean> msgQueue;

    public static AtomicInteger productOffset = new AtomicInteger(0);

    public static AtomicInteger consumerOffset = new AtomicInteger(0);

    public static AtomicInteger exceptionOffset = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        msgQueue = new LinkedBlockingQueue<>(bufferSize);
    }

    public void syncDb() {
        List<SyncPersistenceBean> syncPersistenceBeans = new ArrayList<>();
        try {
            SyncPersistenceBean bean = msgQueue.poll(1, TimeUnit.MILLISECONDS);
            if (Objects.nonNull(bean)) {
                syncPersistenceBeans.add(bean);
                msgQueue.drainTo(syncPersistenceBeans);
                syncPersistenceHandler.doTransaction(syncPersistenceBeans);
                syncPersistenceBeans.clear();
            }
        } catch (Exception e) {
            logger.error("msgQueue exception:{}", e);
        }
    }

    public void sync(ISync async) {
        SyncPersistenceBean msgBean = new SyncPersistenceBean();
        msgBean.setSync(async);
        boolean offer = msgQueue.offer(msgBean);
        if (!offer) {
            logger.error("publish sync failed");
            return;
        }
        productOffset.incrementAndGet();
    }
}
