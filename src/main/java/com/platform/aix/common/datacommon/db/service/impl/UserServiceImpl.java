package com.platform.aix.common.datacommon.db.service.impl;

import com.google.common.collect.Maps;
import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.UserMapper;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.domain.UserExample;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.spring.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public IMybatisMapper<User, String, UserExample> getMapper() {
        return userMapper;
    }

    @Override
    public void coverCache(boolean fromRedis) {

    }

    @Override
    public void clearCache() {

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
        {
            update(user);
        }
    }

    @Override
    protected void delete(String PK) {

    }

    @Override
    public List<User> queryByUserId(String userId) {
        return null;
    }
}
