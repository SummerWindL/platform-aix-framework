package com.platform.aix.service.processor.msg;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author Advance
 * @date 2022年05月17日 15:43
 * @since V1.0.0
 */
@Component
public class MsgBeanHandler implements EventHandler<MsgBean> {
    private static final Logger logger = LoggerFactory.getLogger(MsgBeanHandler.class);
    @Override
    public void onEvent(MsgBean event, long sequence, boolean endOfBatch) throws Exception {

    }
}
