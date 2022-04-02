package com.platform.aix.common.datacommon.sync;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

@Component
public class SyncPersistenceFactory implements EventFactory<SyncPersistenceBean> {

    private SyncPersistenceBean beanTemplate = new SyncPersistenceBean();

    @Override
    public SyncPersistenceBean newInstance() {
        return beanTemplate.clone();
    }
}