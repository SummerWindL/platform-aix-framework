package com.platform.aix.common.listener;

import com.platform.aix.common.spring.AppService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.TimerTask;

/**
 * @author Advance
 * @date 2021年11月19日 15:43
 * @since V1.0.0
 */
public abstract class AppStartupAdapter extends TimerTask implements AppStartupListener{

    Logger logger = LoggerFactory.getLogger(AppStartupAdapter.class);

    @Override
    public void onApplicationStartup(ContextRefreshedEvent event) {
        afterStartup(event.getApplicationContext());
    }
    @Override
    public void run() {
        String runOptimize = AppService.getApplicationContext().getEnvironment().getProperty("application.runOptimize");
        if (!StringUtils.equals(runOptimize, "Y")) {
            logger.info("开始执行一些定时任务......");
        }
    }
}
