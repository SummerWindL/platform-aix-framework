package com.platform.aix.common.datacommon.async;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Advance
 */
@Component
public class AsyncPersistenceFactory implements EventFactory<AsyncPersistenceBean> {

    private AsyncPersistenceBean beanTemplate = new AsyncPersistenceBean();

    @Override
    public AsyncPersistenceBean newInstance() {
        return beanTemplate.clone();
    }
}
