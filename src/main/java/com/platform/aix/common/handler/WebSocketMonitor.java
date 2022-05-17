package com.platform.aix.common.handler;

import com.platform.websocket.service.IWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * websocket关闭时监控器（处理一些关闭的逻辑）
 * @author Advance
 * @date 2022年02月17日 17:39
 * @since V1.0.0
 */
@Service
@Slf4j
public class WebSocketMonitor implements IWebSocketService {

    @Override
    public void invokeCloseSocket(String... token) {
        log.info("{}，关闭了连接",token);
    }
}
