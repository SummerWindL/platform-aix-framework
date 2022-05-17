package com.platform.aix.common.datacommon.async;

import com.platform.aix.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Advance
 */
@Component("asyncPersistenceHandler")
public class AsyncPersistenceHandler {
    private static final Logger logger = LoggerFactory.getLogger(AsyncPersistenceHandler.class);

    @Transactional
    public void doTransaction(List<AsyncPersistenceBean> asyncPersistenceBeans) {
        asyncPersistenceBeans.forEach(asyncPersistenceBean -> asyncData(asyncPersistenceBean));
    }

    private void asyncData(AsyncPersistenceBean asyncPersistenceBean) {
        try {
            asyncPersistenceBean.getAsync().async();
            AsyncPersistenceWorker.consumerOffset.incrementAndGet();
        } catch (Exception e) {
            logger.error("async data: {} error: {}", JsonUtils.getJsonFromObject(asyncPersistenceBean), e.getMessage());
            AsyncPersistenceWorker.exceptionOffset.incrementAndGet();
        }
    }


}
