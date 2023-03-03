package com.platform.aix.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.platform.common.util.StringUtil;
import com.platform.core.cache.CacheKey;
import com.platform.core.constant.GlobalConstant;
import com.platform.core.interf.DataDict;
import com.platform.core.interf.Menu;
import com.platform.core.interf.Page;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

/**
 * @author Advance
 * @date 2022年03月08日 16:38
 * @since V1.0.0
 */
@Lazy(false)
@Component
public class CacheUtil {
    /**
     * 注入缓存管理器
     *
     */
    @Autowired(required = false)
    private CacheManager cacheManager;

    /**
     * 默认缓存key
     */
    public static final String DEFALUT_KEY = "default";

    /**
     * app缓存对象
     */
    private static Cache appCache;

    /**
     * page缓存对象
     */
    private static Cache pageCache;

    /**
     * menu缓存对象
     */
    private static Cache menuCache;

    /**
     * 数据字典缓存对象
     */
    private static Cache dictCache;

    /**
     * 加密解密工具类
     */
    private static StringEncryptor stringEncryptor;


    @PostConstruct
    private void init() {
        if (null == cacheManager) {
            throw new RuntimeException("请配置CacheManager.");
        }
        appCache = cacheManager.getCache(CacheKey.APP_CACHE);
        pageCache = cacheManager.getCache(CacheKey.PAGE_CACHE);
        menuCache = cacheManager.getCache(CacheKey.MENU_CACHE);
        dictCache = cacheManager.getCache(CacheKey.DICT_CACHE);
    }

    /**
     * 注入 StringEncryptor
     * @param stringEncryptor StringEncryptor
     */
    @Autowired
    protected void setStringEncryptor(StringEncryptor stringEncryptor) {
        CacheUtil.stringEncryptor = stringEncryptor;
    }


    /**
     * 添加app缓存，将value缓存到指定的key中
     *
     * @param key 缓存key
     * @param value 被缓存的值
     */
    public static void putAppCache(Object key, Object value) {
        cache(appCache, key, value);
    }

    /**
     * 从app缓存中获取key对应的缓存值，获取{@link CacheUtil#putAppCache(Object, Object)}方法设置的缓存
     *
     * @param cacheKey 缓存key
     * @param type 缓存数据对象类型
     * @param <T> 泛型
     * @return 缓存值
     */
    public static <T> T getAppCache(String cacheKey, Class<T> type) {
        return getCache(appCache, cacheKey, type);
    }

    /**
     * 从app缓存中获取系统参数缓存值
     *
     * @param pattern 系统缓存类别
     * @param paramKey 参数key
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static String getAppParamCache(String pattern, String paramKey) {
        Map<String, String> cacheValue = getAppCache(pattern, Map.class);
        if(null != cacheValue) {
            return StringUtil.trim(cacheValue.get(paramKey));
        }
        return paramKey;
    }

    /**
     * 从app缓存中获取系统参数缓存值
     *
     * @param sysCode 系统编号
     * @param paramKey 参数key
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static String getSysParamCache(String sysCode, String paramKey) {
        Map<String, Map<String, String>> cacheValue = getAppCache(GlobalConstant.SYS_PARA_KEY, Map.class);
        if(null != cacheValue && cacheValue.get(sysCode) != null) {
            return cacheValue.get(sysCode).get(paramKey);
        }
        return paramKey;
    }

    /**
     * 从app缓存中获取系统参数缓存值
     *
     * @param paramKey 参数key
     * @return 缓存值
     */
    public static String getSysParam(String paramKey) {
        String value = getAppParamCache(GlobalConstant.SYS_PARA_KEY, paramKey);
        if(StringUtil.startsWith(value, "ENC(") && StringUtil.endsWith(value, ")")) {
            return stringEncryptor.decrypt(StringUtil.substring(value, 4, value.length() - 1));
        }
        return value;
    }

    /**
     *
     * @param paramKey
     * @param institutionCode
     * @return
     */
    public static String getSpecialSysParam(String paramKey,String institutionCode) {

        if(StringUtils.equals(paramKey, GlobalConstant.PARA_AUTHORIZE_LIMIT)|| StringUtils.equals(paramKey, GlobalConstant.PARA_PRICE_CAL_TYPE)
                ||StringUtils.equals(paramKey, GlobalConstant.PARA_PORTFOLIO_HANDLE_USERID) ||  StringUtils.equals(paramKey, GlobalConstant.PARA_SETTLE_ACCT_NAME) ||
                StringUtils.equals(paramKey, GlobalConstant.PARA_SETTLE_ACCT_NO)|| StringUtils.equals(paramKey, GlobalConstant.PARA_PROD_TRU_ACCT_NO)
                || StringUtils.equals(paramKey, GlobalConstant.PARA_PROD_TRU_ACCT_NAME) || StringUtils.equals(paramKey, GlobalConstant.PARA_LENDING_ACCT_NO) || StringUtils.equals(paramKey, GlobalConstant.PARA_LENDING_ACCT_NAME)  ){

            if(StringUtil.isNotEmpty(institutionCode)){
                String sysParam = getSysParam(paramKey + "||" + institutionCode);
                if(StringUtils.isEmpty(sysParam)){
                    return getSysParam(paramKey);
                }
                return sysParam;
            }

        }
        return getSysParam(paramKey);
    }

    /**
     * 从app缓存中获取default对应的缓存值，并返回指定类型
     *
     * @param type 数据类型
     * @param <T> 返回数据类型
     * @return 缓存值
     */
    public static <T> T getAppCache(Class<T> type) {
        return getCache(appCache, DEFALUT_KEY, type);
    }

    /**
     * 从app缓存中获取key对应的缓存值，并返回指定类型
     *
     * @param key 缓存key
     * @param type 缓存数据类型
     * @param <T> 返回值类型
     * @return 缓存值
     */
    public static <T> T getAppCache(Object key, Class<T> type) {
        return getCache(appCache, key, type);
    }

    /**
     * 设置画面缓存到default中
     *
     * @param value 画面缓存值
     */
    public static void putPageCache(Map<String, Page> value) {
        cache(pageCache, DEFALUT_KEY, value);
    }

    /**
     * 从画面缓存中获取画面
     *
     * @param pageId 画面ID
     * @return 画面对象
     */
    @SuppressWarnings("unchecked")
    public static Page getPage(String pageId) {
        Map<String, Page> pageMap = getPages();
        if (pageMap != null) {
            return pageMap.get(pageId);
        }
        return null;
    }

    /**
     * 从画面缓存中获取所有画面
     * @return Map
     */
    @SuppressWarnings({"rawtypes"})
    public static Map getPages(){
        return getCache(pageCache, DEFALUT_KEY, Map.class);
    }

    /**
     * 设置菜单缓存到default中
     *
     * @param value 菜单缓存值
     */
    public static void putMenuCache(Map<String, Menu> value) {
        cache(menuCache, DEFALUT_KEY, value);
    }

    /**
     * 从菜单缓存中获取菜单
     *
     * @return 菜单对象
     */
    @SuppressWarnings("unchecked")
    public static Collection<Menu> getAllMenu() {
        Map<String, Menu> menuMap = getCache(menuCache, DEFALUT_KEY, Map.class);
        if(null != menuMap) {
            return menuMap.values();
        }
        return null;
    }

    /**
     * 从菜单缓存中获取菜单
     *
     * @param menuId 菜单ID
     * @return 菜单对象
     */
    @SuppressWarnings("unchecked")
    public static Menu getMenu(String menuId) {
        Map<String, Menu> menuMap = getCache(menuCache, DEFALUT_KEY, Map.class);
        if (MapUtils.isNotEmpty(menuMap)) {
            return findMenuById(menuMap.values(), menuId);
        }
        return null;
    }

    private static Menu findMenuById(Collection<Menu> menus, String menuId) {
        Menu temp;
        if(CollectionUtil.isNotEmpty(menus)) {
            for (Menu menu : menus) {
                if(StringUtils.equals(menu.getId(), menuId)) {
                    temp = menu;
                }
                else {
                    temp = findMenuById(menu.getChildren(), menuId);
                }
                if(temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据字典缓存值
     *
     * @param value 缓存值
     */
    public static void putDictCache(Map<String, DataDict> value) {
        cache(dictCache, DEFALUT_KEY, value);
    }

    /**
     * 获取数据字典缓存
     * @param dictCode 数据字典代码
     * @return 数据字典
     */
    public static DataDict getDictCode(String dictCode) {
        Map<String, DataDict> dictMap = getAllDictCode();
        if (dictMap != null) {
            return dictMap.get(dictCode);
        }
        return null;
    }

    /**
     * 获取所有数据字典缓存
     *
     * @return 数据字典
     */
    @SuppressWarnings("unchecked")
    public static Map<String, DataDict> getAllDictCode() {
        return getCache(dictCache, DEFALUT_KEY, Map.class);
    }

    /**
     * 清除所有app缓存
     */
    public static void clearAllAppCache() {
        clear(appCache);
    }

    /**
     * 清除app缓存
     */
    public static void clearAppCache() {
        clear(appCache, DEFALUT_KEY);
    }

    /**
     * 清除app缓存中指定key的缓存
     * @param key 缓存key
     */
    public static void clearAppCache(Object key) {
        clear(appCache, key);
    }

    /**
     * 清除所有page缓存
     */
    public static void clearPageCache() {
        clear(pageCache, DEFALUT_KEY);
    }

    /**
     * 清除数据字典缓存
     */
    public static void clearDictCache() {
        clear(dictCache, DEFALUT_KEY);
    }

    /**
     * 清空所有缓存
     */
    public static void clearAll() {
        clear(appCache);
        clear(pageCache);
        clear(dictCache);
    }

    /**
     * 清除缓存
     * @param cache 缓存
     * @param key 缓存key
     */
    private static void clear(Cache cache, Object key) {
        if (null != cache) {
            cache.evict(key);
        }
    }

    /**
     * 获取缓存
     * @param cache 缓存
     * @param key 缓存key
     * @param type 缓存值类型
     * @param <T> 返回值类型
     * @return 缓存值
     */
    private static <T> T getCache(Cache cache, Object key, Class<T> type) {
        try {
            return cache.get(key, type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 清空缓存
     * @param cache 缓存
     */
    private static void clear(Cache cache) {
        if (null != cache) {
            cache.clear();
        }
    }

    /**
     * @param cache 缓存
     * @param key 缓存key
     * @param value 缓存值
     */
    private static synchronized void cache(Cache cache, Object key, Object value) {
        if (null != cache) {
            cache.put(key, value);
        }
    }

    /**
     * 从app缓存中获取系统机构缓存值
     * add by wj 20200529 二级法人改造
     * @param institutionCode 机构代码
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static Object getAppCacheInstitution(String institutionCode) {
        Map<String, Object> cacheValue = getAppCache(GlobalConstant.SYS_INSTITUTION_KEY, Map.class);
        if(null != cacheValue) {
            return cacheValue.getOrDefault(institutionCode,null);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object getAppCacheInstitutionCodeName(String institutionCode) {
        Map<String, Object> cacheValue = getAppCache(GlobalConstant.SYS_INSTITUTION_CODE_NAME_KEY, Map.class);
        if (null != cacheValue) {
            return cacheValue.getOrDefault(institutionCode, null);
        }
        return null;
    }

    /**
     * 从app缓存中获取 过滤配置 缓存值
     * add by wj 20200529 二级法人改造
     * @param sqlId sqlId
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static Object getAppCacheFilterMapperConfig(String sqlId) {
        Map<String, Object> cacheValue = getAppCache(GlobalConstant.FILTER_MAPPER_CONFIG, Map.class);
        if(null != cacheValue) {
            return cacheValue.getOrDefault(sqlId,null);
        }
        return null;
    }


}
