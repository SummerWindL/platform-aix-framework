package com.platform.aix.service.processor.msg;

import com.lmax.disruptor.EventHandler;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.service.processor.HospitalElectronicPaperProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费者
 * @author Advance
 * @date 2022年05月17日 15:43
 * @since V1.0.0
 */
@Component
public class MsgBeanHandler implements EventHandler<MsgBean> {
    private static final Logger logger = LoggerFactory.getLogger(MsgBeanHandler.class);
    @Autowired
    private HospitalElectronicPaperProcessor hospitalElectronicPaperProcessor;
    @Override
    public void onEvent(MsgBean msgBean, long sequence, boolean endOfBatch) throws Exception {
        TradeMsgReceiver.consumerOffset.incrementAndGet();
        switch (msgBean.getMsgType()) {
            case MsgType.HOST_REGIST_INFO:{
                //医院登记逻辑
                logger.info("医院信息登记=-------=");
                hospitalElectronicPaperProcessor.createUserElectonicPaper((List<User>) msgBean.getMsg());
                break;
            }
            case MsgType.HOST_RECORD_SIGN:{
                //体征检查逻辑
                logger.info("体征检查=-------=");
                break;
            }
            case MsgType.HOST_USER_PAY:
        }
    }
}
