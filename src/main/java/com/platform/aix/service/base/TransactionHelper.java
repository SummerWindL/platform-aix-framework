package com.platform.aix.service.base;

import com.alibaba.fastjson.JSONArray;
import com.cluster.platform.redis.common.IValue;
import com.cluster.platform.redis.common.Value;
import com.google.common.collect.Lists;
import com.platform.aix.common.constants.DBSaveType;
import com.platform.aix.common.datacommon.cache.config.DataServiceConfig;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.service.redisservice.param.service.UserInfoParamValService;
import com.platform.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author Advance
 * @date 2022年03月26日 12:26
 * @since V1.0.0
 */
@Component
public class TransactionHelper implements ApplicationListener<ApplicationReadyEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ThreadLocal<ParamContext> context = new ThreadLocal<ParamContext>() {
        public ParamContext initialValue() {
            return new ParamContext();
        }
    };

    @Autowired
    private UserService userService;
    @Autowired
    private DataServiceConfig dataServiceConfig;

    @PostConstruct
    public void start() {
    }
    @PreDestroy
    public void stop() throws Exception {
    }

    public void save(){
        int dbSaveType = dataServiceConfig.isAsyncDb() ? DBSaveType.ASYNC.value : DBSaveType.SYNC_BATCH.value;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //项目一起动就执行该方法
        logger.info("start {} , {}","TransactionHelper","onApplicationEvent");
        //执行数据库查询 一些基础数据到 内存中
        List<User> users = Lists.newArrayList(); //模拟数据库查询
        User user = new User();
        user.setId("1");
        user.setUsername("张三");
        User user2 = new User();
        user2.setId("2");
        user2.setUsername("李四");
        users.add(user);
        users.add(user2);
        logger.info("onApplicationEvent users :{}", JSONArray.toJSONString(users));
        if (Objects.nonNull(users)) {
            userService.saveUserInfo(users);
        }
    }


}
