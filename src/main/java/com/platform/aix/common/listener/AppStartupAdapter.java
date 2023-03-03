package com.platform.aix.common.listener;

import com.platform.aix.common.spring.AppService;
import com.platform.aix.common.util.CacheUtil;
import com.platform.core.convert.DateConverter;
import com.platform.core.interf.DataDict;
import com.platform.core.interf.Menu;
import com.platform.core.interf.Page;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 服务启动默认监听
 * @author Advance
 * @date 2021年11月19日 15:43
 * @since V1.0.0
 */
public abstract class AppStartupAdapter extends TimerTask implements AppStartupListener{

    Logger logger = LoggerFactory.getLogger(AppStartupAdapter.class);
    /**
     * 缓存刷新时间，默认为20分钟
     */
    private long cacheRefreshTime = 20 * 60 * 1000;

    /**
     * 画面缓存（该方法会根据“缓存刷新时间”进行刷新）
     *
     * @return Map<String, Serializable>
     */
    protected abstract Map<String, Page> pageCache();

    /**
     * 菜单缓存（该方法会根据“缓存刷新时间”进行刷新）
     *
     * @return Map<String, Serializable>
     */
    protected abstract Map<String, Menu> menuCache();

    /**
     * 数据字典缓存（该方法会根据“缓存刷新时间”进行刷新）
     *
     * @return Map<String, Serializable>
     */
    protected abstract Map<String, DataDict> dictCodeCache();

    @Override
    public void onApplicationStartup(ContextRefreshedEvent event) {
        long time = getCacheRefreshTime();
        if (time > 0) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(this, 1000, time);
        }
        registerConverter();
        afterStartup(event.getApplicationContext());
    }
    @Override
    public void run() {
        String runOptimize = AppService.getApplicationContext().getEnvironment().getProperty("application.runOptimize");
        if (!StringUtils.equals(runOptimize, "Y")) {
            logger.info("开始执行一些定时任务......");
        }
        logger.info("开始刷新缓存...");
        CacheUtil.putDictCache(dictCodeCache());
        CacheUtil.putPageCache(pageCache());
        CacheUtil.putMenuCache(menuCache());
    }
    /**
     * 向apache beanutil添加Converter
     */
    private void registerConverter() {
        ConvertUtils.register(new DateConverter(), Date.class);
    }
    /**
     * 缓存刷新时间（单位：毫秒）
     *
     * @return 毫秒
     */
    public long getCacheRefreshTime() {
        return cacheRefreshTime;
    }

    /**
     * 设置缓存刷新时间
     *
     * @param cacheRefreshTime 毫秒数
     */
    public void setCacheRefreshTime(long cacheRefreshTime) {
        this.cacheRefreshTime = cacheRefreshTime;
    }
}
