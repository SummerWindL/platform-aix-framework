package com.platform.aix.service.redisservice;

import com.cluster.platform.redis.constants.RedisConstant;
import com.cluster.platform.redis.core.PipeReadService;
import com.cluster.platform.redis.core.PipeWriteService;
import com.cluster.platform.redis.data.RedisHash;
import com.platform.aix.service.redisservice.entity.IBusiness;
import com.platform.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 基础对外的redis服务
 * @author Advance
 * @date 2022年05月20日 14:59
 * @since V1.0.0
 */
public class RedisBaseService <V extends IBusiness>{
    @Autowired
    private PipeReadService<String, String, V> reader;

    @Autowired
    private PipeWriteService<String, String, V> writer;
    /**
     * 批量保存数据到Redis
     *
     * @param vList 数据数组
     * @see
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public void saveToRedis(V... vList) {
        if (vList == null || vList.length == 0) {
            return;
        }
        Map<String, Map<String, V>> infoList = new HashMap<>(vList.length);
        for (V v : vList) {
            String k = v.getKey();
            Map<String, V> map = infoList.getOrDefault(k, new HashMap<>());
            map.put(v.getField(), v);
            infoList.put(k, map);
        }
        writer.writeList(infoList);
    }

    /**
     * 从Redis获取数据，指定条件
     *
     * @param key Redis主键
     * @param fieldList Redis field
     * @return 数据
     * @see
     * @since 1.0
     */
    public Map<String, V> getMapFromRedis(final String key, Collection<String> fieldList) {
        if (CollectionUtils.isEmpty(fieldList)) {
            return new HashMap<>();
        }
        Map<String, Map<String, V>> vList = new HashMap<>();
        Map<String, V> map = new HashMap<>();
        fieldList.forEach(str -> map.put(str, null));
        vList.put(key, map);
        return reader.readList(vList).get(key);
    }

    /**
     * 根据key field 取得Redis内容
     *
     * @param key RedisKey
     * @param field HashField
     * @return Redis内容
     * @see
     * @since 1.0
     */
    public V getInfoFromRedis(String key, String field) {
        RedisHash<String, String, V> hash = new RedisHash<>();
        hash.setKey(key);
        hash.setField(field);
        return reader.read(hash);
    }

    public List<String> allHKeys(String key, String field) {
        return reader.allHKeys(key, RedisConstant.STAR + field + RedisConstant.STAR);
    }

    /**
     * 删除Redis数据
     *
     * @param key Redis Key
     * @param field Redis hash key
     * @see
     * @since 1.0
     */
    public void deleteInfoFromRedis(String key, String... field) {
        if (field == null || field.length == 0) {
            RedisHash<String, String, V> hash = new RedisHash<>();
            hash.setKey(key);
            writer.deleteAll(hash);
        }
        else {
            Map<String, Map<String, V>> vList = new HashMap<>();
            Map<String, V> map = new HashMap<>();
            for (String str : field) {
                map.put(str, null);
            }
            vList.put(key, map);
            writer.deleteList(vList);
        }
    }

    public Map<String, Map<String, V>> readAll(List<String> keyList) {
        return reader.readAll(keyList);
    }

    /**
     * 获取 reader
     *
     * @return reader.
     */
    public PipeReadService<String, String, V> getReader() {
        return reader;
    }

    /**
     * 设置 reader
     *
     * @param reader reader
     */
    public void setReader(PipeReadService<String, String, V> reader) {
        this.reader = reader;
    }

    /**
     * 获取 writer
     *
     * @return writer.
     */
    public PipeWriteService<String, String, V> getWriter() {
        return writer;
    }

    /**
     * 设置 writer
     *
     * @param writer writer
     */
    public void setWriter(PipeWriteService<String, String, V> writer) {
        this.writer = writer;
    }

    /**
     * 清除Redis内所有数据
     *
     * @see
     * @since 1.0
     */
    public void flushDB() {
        List<String> keys = this.reader.allKeys(RedisConstant.STAR + "RSK{" + RedisConstant.STAR);
        if (!StringUtil.isEmptyList(keys)) {
            keys.forEach(key -> flushDBHashFiled(key, null));
        }
    }

    /**
     * 清除Redis内所有数据
     *
     * @param keyArr 排除不删除的
     * @see
     * @since 1.0
     */
    public void flushDBExceptKey(String... keyArr) {
        List<String> keys = this.reader.allKeys(RedisConstant.STAR + "RSK{" + RedisConstant.STAR);
        if (!StringUtil.isEmptyList(keys)) {
            List<String> excKeys = keyArr != null && keyArr.length > 0 ? Arrays.asList(keyArr) : new ArrayList<>();
            keys.forEach(key -> {
                if (excKeys.contains(key)) {
                    return;
                }
                flushDBHashFiled(key, null);
            });
        }
    }

    /**
     * 模糊删除
     *
     * @param key Redis key
     * @param field 模糊字段
     */
    public void flushDBHashFiled(String key, String field) {
        List<String> keys = this.reader.allHKeys(key, field);
        if (!StringUtil.isEmptyList(keys)) {
            Map<String, Map<String, V>> hash = new HashMap<>();
            Map<String, V> temp = new HashMap<>();
            keys.forEach(f ->temp.put(f, null));
            hash.put(key, temp);
            this.writer.deleteList(hash);
        }
    }
}
