package com.platform.aix.service.processor.msg;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

@Component
public class MsgBeanFactory implements EventFactory<MsgBean> {
    private MsgBean msgBean = new MsgBean();

    @Override
    public MsgBean newInstance() {
        return msgBean.clone();
    }
}
