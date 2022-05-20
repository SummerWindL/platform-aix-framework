package com.platform.aix.service.redisservice.param.service;

import com.cluster.platform.redis.common.IValue;
import com.cluster.platform.redis.constants.RedisConstant;
import com.cluster.platform.redis.core.PipeReadService;
import com.cluster.platform.redis.core.PipeWriteService;
import com.platform.aix.common.constants.RedisKeyEnum;
import com.platform.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息参数读写服务
 * @author Advance
 * @date 2022年05月20日 15:41
 * @since V1.0.0
 */
@Service
public class UserInfoParamValService {
    /**
     * 指标参数值读信息服务
     */
    @Autowired
    private PipeReadService<String, String, IValue<Object>> userInfoReadSrv;

    /**
     * 指标参数值写信息服务
     */
    @Autowired
    private PipeWriteService<String, String, IValue<Object>> userInfoWriteSrv;


    /**
     * 删除 删除指标参数值
     *
     * @param paraValMap 指标参数值列表
     * @see
     * @since 1.0
     */
    public void delParamVal(Map<String, Map<String, IValue<Object>>> paraValMap) {
        if (CollectionUtils.isEmpty(paraValMap)) {
            return;
        }
        // 删除Redis参数值
        userInfoWriteSrv.deleteList(changeKey(paraValMap, true));
    }

    /**
     * 更新 所有指标参数值
     *
     * @param paraValMap 指标参数值列表
     * @see
     * @since 1.0
     */
    public void setParamVal(Map<String, Map<String, IValue<Object>>> paraValMap) {
        if (CollectionUtils.isEmpty(paraValMap)) {
            return;
        }
        // 更新Redis参数值
        userInfoWriteSrv.writeList(changeKey(paraValMap, true));
    }

    /**
     * 取得 所有指标参数值
     *
     * @param paraValList 指标参数查询条件
     * @return 所有指标参数值 {@link Map}<参数代码,参数值>
     * @see
     * @since 1.0
     */
    public Map<String, Map<String, IValue<Object>>> getParamVal(Map<String, Map<String, IValue<Object>>> paraValList) {
        if (CollectionUtils.isEmpty(paraValList)) {
            return new HashMap<>();
        }
        // 从Redis取得参数值
        Map<String, Map<String, IValue<Object>>> res = userInfoReadSrv.readList(
                changeKey(paraValList, true));
        return changeKey(res, false);
    }

    private Map<String, Map<String, IValue<Object>>> changeKey(Map<String, Map<String, IValue<Object>>> paraValList,
                                                               boolean isForRedis) {
        Map<String, Map<String, IValue<Object>>> paramList = new HashMap<>();
        String header = RedisKeyEnum.指标参数值.key();
        paraValList.entrySet().forEach(en -> {
            String key = en.getKey();
            if (isForRedis) {
                key = header + key;
            }
            else {
                key = StringUtil.remove(key, header);
            }
            Map<String, IValue<Object>> val = en.getValue();
            paramList.put(key, val);
        });
        return paramList;
    }

    private List<String> changeKey(List<String> keys, boolean isForRedis) {
        String header = RedisKeyEnum.指标参数值.key();
        List<String> resList = new ArrayList<>();
        keys.forEach(k -> {
            if (isForRedis) {
                resList.add(header + k);
            }
            else {
                resList.add(StringUtil.remove(k, header));
            }
        });
        return resList;
    }

    public Map<String, Map<String, IValue<Object>>> readAll(List<String> keyList) {
        return changeKey(userInfoReadSrv.readAll(changeKey(keyList, true)), false);
    }

    public List<String> allKeys(String key) {
        return changeKey(userInfoReadSrv.allKeys(RedisConstant.STAR + RedisKeyEnum.指标参数值.key() + key),
                false);
    }

    public List<String> allHKeys(String key, String field) {
        String header = RedisKeyEnum.指标参数值.key();
        return userInfoReadSrv.allHKeys(header + key, RedisConstant.STAR + field + RedisConstant.STAR);
    }
}
