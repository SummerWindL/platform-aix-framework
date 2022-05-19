package com.platform.aix.service.processor.msg;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MsgReceiverManager {
    @Value("${trade.thread.msg.num}")
    private int msgReceiverSize;

    private List<TradeMsgReceiver> msgReceivers;

    @Autowired
    private UserService userService;

    // 账号ID映射线程
    private Map<String, Integer> msgReceiverMapping = Maps.newConcurrentMap();

    // 线程id对应数量
    private Map<Integer, Integer> msgCounts = Maps.newConcurrentMap();


    public void init() {
        msgReceivers = Lists.newArrayListWithCapacity(msgReceiverSize);
        for (int i = 0; i < msgReceiverSize; i++) {
            msgReceivers.add(AppService.getBean(TradeMsgReceiver.class));
            msgCounts.put(i, 0);
        }
        initMsgReceiver();
    }

    public TradeMsgReceiver getMsgReceiverByUserId(String userId) {
        Integer threadIndex = msgReceiverMapping.get(userId);
        if (Objects.isNull(threadIndex)) {
            threadIndex = 0;
            int minCount = msgCounts.get(0);
            for (Map.Entry<Integer, Integer> entry : msgCounts.entrySet()) {
                if (entry.getValue() < minCount) {
                    threadIndex = entry.getKey();
                    minCount = entry.getValue();
                }
            }
            msgReceiverMapping.put(userId, threadIndex);
            msgCounts.put(threadIndex, minCount + 1);
        }
        return msgReceivers.get(threadIndex);
    }

    /**
     * 初始化消息队列
     */
    private void initMsgReceiver() {
        //从数据库查找所有的用户对应的消息队列
        userService.findAll().forEach(userBean ->getMsgReceiverByUserId(userBean.getId()));
    }

    public void clearMsgReceiverMapping() {
        msgReceiverMapping.clear();
    }
}
