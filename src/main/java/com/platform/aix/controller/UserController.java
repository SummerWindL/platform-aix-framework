package com.platform.aix.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/add")
    public void testRequestValidate(@RequestBody List<User> users){
        log.info("[开始插入User对象]");
        userService.asyncUpdate(users);
    }
}
