package com.platform.aix.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Advance
 * @date 2021年11月19日 15:45
 * @since V1.0.0
 */
@Component
@Slf4j
public class StartupConfiguration extends AppStartupAdapter {

    @Override
    public void afterStartup(ApplicationContext applicationContext) {
        log.info("系统成功启动..");

    }
}
