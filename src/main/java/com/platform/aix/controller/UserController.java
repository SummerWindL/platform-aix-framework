package com.platform.aix.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.spring.AppService;
import com.platform.aix.service.security.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Advance
 * @date 2022年03月26日 13:02
 * @since V1.0.0
 */
@RestController(value = "user")
@RequestMapping("/user")
@Slf4j
@DS("slave_1")
public class UserController {

    @Autowired
    private UserService userService;
    private ThreadLocal<UserService> userServiceThreadLocal = ThreadLocal.withInitial(()
            -> AppService.getBean(UserService.class));

    @PostMapping("/add")
    public void testRequestValidate(@RequestBody List<User> users){
        log.info("[开始插入User对象]");
        userServiceThreadLocal.get().asyncUpdate(users);
    }


    @GetMapping("/getUser")
    public void testInterceptor(){
        log.info(UserUtil.getUserName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserController that = (UserController) o;
        return Objects.equals(userServiceThreadLocal.get(), that.userServiceThreadLocal.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userServiceThreadLocal.get());
    }
}
