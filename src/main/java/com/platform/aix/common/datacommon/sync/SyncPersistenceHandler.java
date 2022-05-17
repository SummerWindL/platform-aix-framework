package com.platform.aix.common.datacommon.sync;

import com.platform.aix.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SyncPersistenceHandler {
    private static final Logger logger = LoggerFactory.getLogger(SyncPersistenceHandler.class);

    @Transactional
    public void doTransaction(List<SyncPersistenceBean> syncPersistenceBeans) {
        syncPersistenceBeans.forEach(syncPersistenceBean -> asyncData(syncPersistenceBean));
    }

    private void asyncData(SyncPersistenceBean syncPersistenceBean) {
        try {
            syncPersistenceBean.getSync().sync();
            SyncPersistenceWorker.consumerOffset.incrementAndGet();
        } catch (Exception e) {
            logger.error("sync data: {} error: {}", JsonUtils.getJsonFromObject(syncPersistenceBean), e.getMessage());
            SyncPersistenceWorker.exceptionOffset.incrementAndGet();
        }
    }
}