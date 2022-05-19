package com.platform.aix.common.datacommon.db.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.UserMapper;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.domain.UserExample;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.spring.AppService;
import com.platform.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Advance
 * @date 2022年03月26日 11:48
 * @since V1.0.0
 */
@Service
public class UserServiceImpl extends AsyncServiceImpl<String, User> implements UserService {

    private Map<String, Map<String, User>> userIdMap = Maps.newConcurrentMap();
    private Map<String, User> usersMap = new ConcurrentHashMap<>();
    @Autowired
    private UserMapper userMapper;

    @Override
    public IMybatisMapper<User, String, UserExample> getMapper() {
        return userMapper;
    }

    @Override
    public void coverCache(boolean fromRedis) {
        clearCache();
        List<User> instructionList = fromRedis ? getAllFromRedis() : userMapper.selectByExample(null);
        List<User> instructionCache = new ArrayList<>();
        if (Objects.nonNull(instructionList)) {
//            instructionList.forEach(instruction -> {
//                logger.info("origin cache instructionId:{},tradeId:{}",instruction.getEntrustId(),instruction.getTradeId());
//                logger.info("origin cache instruction:{}",com.joyintech.framework.util.JsonUtils.object2Json(instruction));
//                if (!Strings.isNullOrEmpty(instruction.getTradersInfo())) {
//                    List<String> traderSet = JsonUtils.getObjectListFromJson(instruction.getTradersInfo(), String.class);
//                    if(InstructionStatus.WAIT_RECEIVE.value.equals(instruction.getEntrustStatus())) {//未接收缓存到每个用户
//                        if (Objects.nonNull(traderSet) && traderSet.size() > 0) {
//                            traderSet.forEach(s -> {
//                                if (!s.equals(instruction.getTradeId())) {
//                                    Instruction i = instruction.cloneInstruction();
//                                    i.setTradeId(s);
//                                    instructionCache.add(i);
//                                    logger.info("new cache instruction:{}",com.joyintech.framework.util.JsonUtils.object2Json(i));
//                                    logger.info("new cache instructionId:{},tradeId:{}",i.getEntrustId(),i.getTradeId());
//                                }
//                            });
//                        }
//                    }
//                }
//            });
            if (instructionCache.size() > 0) updateCache(instructionCache);
            if (instructionList.size() > 0) updateCache(instructionList);
        }
    }

    @Override
    public void clearCache() {
        memory_cache.clear();
        userIdMap.clear();
    }

    @Override
    protected void update(User user) {
        Map<String, User> userDetailMap = userIdMap.get(user.getId());
        if (Objects.isNull(userDetailMap)) {
            userDetailMap = new ConcurrentHashMap<>();
            userIdMap.put(user.getId(), userDetailMap);
        }
        userDetailMap.put(user.getId(), user);
    }

    @Override
    protected void save(User user) {
       update(user);
    }

    @Override
    protected void delete(String PK) {

    }

    @Override
    public User queryByUserId(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 一启动就执行
     * @param userList
     */
    @Override
    public void saveUserInfo(List<User> userList) {
        usersMap.clear();
        userList.forEach(user -> {
            if(!StringUtil.isEmpty(user.getId())){
                String key = user.getId();
                usersMap.put(key,user);
                //插入redis缓存
                asyncUpdate(user);
            }

        });

    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectByExample(null);
    }
}
