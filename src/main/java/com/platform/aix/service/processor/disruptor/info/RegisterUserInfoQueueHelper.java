package com.platform.aix.service.processor.disruptor.info;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.platform.aix.service.processor.disruptor.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Advance
 * @date 2022年05月18日 9:28
 * @since V1.0.0
 */
@Component
public class RegisterUserInfoQueueHelper extends BaseQueueHelper<SeriesData, SeriesDataEvent, RegisterUserInformationHandler> implements InitializingBean {
    private static final int QUEUE_SIZE = 1024;
    @Autowired
    private List<RegisterUserInformationHandler> registerUserInformationHandler;
    @Override
    protected int getQueueSize() {
        return QUEUE_SIZE;
    }

    @Override
    protected EventFactory<SeriesDataEvent> eventFactory() {
        return new EventFactory();
    }

    @Override
    protected WorkHandler[] getHandler() {
        int size = registerUserInformationHandler.size();
        RegisterUserInformationHandler[] paramEventHandlers = (RegisterUserInformationHandler[]) registerUserInformationHandler.toArray(new RegisterUserInformationHandler[size]);
        return paramEventHandlers;
    }

    @Override
    protected WaitStrategy getStrategy() {
        return new BlockingWaitStrategy();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
