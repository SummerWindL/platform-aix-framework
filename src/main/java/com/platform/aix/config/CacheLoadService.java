package com.platform.aix.config;

import com.platform.core.interf.DataDict;
import com.platform.core.interf.Menu;
import com.platform.core.interf.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Advance
 * @date 2022年06月22日 14:36
 * @since V1.0.0
 */
@Service
public class CacheLoadService {
    //@Autowired
    //SysPageService
    //@Autowired
    //SysMenuService
    //@Autowired
    //SysDictService
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 画面缓存
     *
     * @return Map<String, Page>
     */
    public Map<String, Page> pageCache() {
        if (true) {
            //通过pageService 获取页面缓存信息
        } else {
            logger.warn("无法读取画面信息，请确认是否实现了SysPageService接口..");
        }
        return null;
    }

    /**
     * 菜单缓存
     *
     * @return Map<String, Menu>
     */
    public Map<String, Menu> menuCache() {
        if (true) {
            //通过menuService 获取菜单缓存信息
        } else {
            logger.warn("无法读取菜单信息，请确认是否实现了SysMenuService接口..");
        }
        return null;
    }

    /**
     * 数据字典缓存
     *
     * @return Map<String, DataDict>
     */
    public Map<String, DataDict> dictCodeCache() {
        if (true) {
            //通过dictCodeService 获取字典缓存信息
        } else {
            logger.warn("无法读取数据字典信息，请确认是否实现了SysDictService接口..");
        }
        return null;
    }
}
