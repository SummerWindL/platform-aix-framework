package com.platform.aix.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import com.platform.aix.cmd.bean.Account;
import com.platform.aix.cmd.bean.response.UserInfo;
import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.service.UserService;
import com.platform.aix.common.exception.AixException;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.util.CacheUtil;
import com.platform.aix.service.processor.disruptor.SeriesData;
import com.platform.aix.service.processor.disruptor.SeriesDataEventQueueHelper;
import com.platform.aix.service.processor.disruptor.info.RegisterUserInfoEventTranslator;
import com.platform.aix.service.processor.disruptor.info.RegisterUserInfoQueueHelper;
import com.platform.aix.service.processor.disruptor.info.RegisterUserInformationHandler;
import com.platform.aix.service.processor.msg.MsgReceiverManager;
import com.platform.aix.service.processor.msg.MsgType;
import com.platform.aix.service.processor.msg.TradeMsgReceiver;
import com.platform.common.util.JsonAdaptor;
import com.platform.comservice.check.CommonCheckHelper;
import com.platform.comservice.door.annotation.DoDoor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Advance
 * @date 2022年03月02日 17:19
 * @since V1.0.0
 */
@RestController(value = "helloWorld")
@RequestMapping("/hello")
@Slf4j
public class HellowController extends CommonCheckHelper {

    @DoDoor(key = "userId",returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用于拦截！\"}",value = Request.class)
    @PostMapping("/api/queryUserInfo")
    public UserInfo queryUserInfo(@RequestParam String userId,@RequestBody @Validated Request request){
        log.info("获取到前端请求");
        return new UserInfo();
    }

    @PostMapping("/testRequest")
    public void testRequestValidate(@RequestBody Request request){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",request.getUserId());
        map.put("password",request.getPassword());
        checkBussinessFunc(Request.class,map);
    }

    @Autowired
    private CacheUtil cache;

    @PostMapping("/world")
    public String helloWord(){
        cache.putAppCache("a","1111111");
        System.out.println("Hello World!!!");
        System.out.printf(cache.getAppCache("a",String.class));
        System.out.printf(cache.getSysParam("a"));
        return "Hello World!!!";
    }

    @GetMapping("/get")
    public String getString(){
        System.out.println("11111");
        return "11111";
    }

    @PostMapping("/func")
    public APIResponse testFunc(){
        String inputBean="1";
        String id = "3";
        return new APIResponse(executeResult(inputBean,id,
                x -> {
                    int affectRows = 0;
                    log.info("X:{}",x);
                    affectRows += 1;//模拟插入成功
                    if(true){
                        affectRows += 1;
                    }
                    return affectRows;
                }
        ));
    }

    private Response executeResult(String inputBean, String id, Function<String,Integer> func){
        Response res = new Response();
        int affectRows = func.apply(id);
        log.info("affectRows is {}", affectRows);
        res.setAffectRows(affectRows);
        res.setMessage("执行插入成功");
        return res;
    }

    @Autowired
    private SeriesDataEventQueueHelper seriesDataEventQueueHelper;
    @GetMapping("demo")
    public void demo(){
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.putIfAbsent("1","hello word");
        objectObjectConcurrentMap.putIfAbsent("2",123456);
        objectObjectConcurrentMap.putIfAbsent("3",new Date());
        objectObjectConcurrentMap.putIfAbsent("4",new Account());
        seriesDataEventQueueHelper.publishEvent(new SeriesData(JSONObject.toJSONString(objectObjectConcurrentMap)));

    }

    @Autowired
    private RegisterUserInfoQueueHelper registerUserInfoQueueHelper;
    @GetMapping("demo2")
    public void demo2(){
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.putIfAbsent("1","hello word");
        objectObjectConcurrentMap.putIfAbsent("2",123456);
        objectObjectConcurrentMap.putIfAbsent("3",new Date());
        objectObjectConcurrentMap.putIfAbsent("4",new Account());
        RegisterUserInfoEventTranslator translator = new RegisterUserInfoEventTranslator();
        registerUserInfoQueueHelper.publishEvent(new SeriesData(JSONObject.toJSONString(objectObjectConcurrentMap)));

    }

    @GetMapping("demo1")
    public void demo1(){
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.putIfAbsent("1","hello word");
        objectObjectConcurrentMap.putIfAbsent("2",123456);
        objectObjectConcurrentMap.putIfAbsent("3",new Date());
        objectObjectConcurrentMap.putIfAbsent("4",new Account());
        //指定生产者
        seriesDataEventQueueHelper.publishEvent(new SeriesData(JSONObject.toJSONString(objectObjectConcurrentMap),this.getClass(),this.getClass()));

    }

    @Autowired
    MsgReceiverManager msgReceiverManager;
    @Autowired
    private UserService userService;
    @GetMapping("demo3")
    @ResponseBody
    public APIResponse demo3(){
        ConcurrentMap<Object, Object> objectObjectConcurrentMap = Maps.newConcurrentMap();
        objectObjectConcurrentMap.putIfAbsent("userName","1");
        objectObjectConcurrentMap.putIfAbsent("age","18");
        TradeMsgReceiver tradeMsgReceiver = msgReceiverManager.getMsgReceiverByUserId("1");
        List<User> users = userService.findAll();
        //测试切面数据
        User newUser = new User();
        newUser.setUsername("王五");
        newUser.setId("3");
        userService.getUser(newUser);
        //第一种形式的发送
        tradeMsgReceiver.addMsg(MsgType.HOST_REGIST_INFO,users);

        seriesDataEventQueueHelper.publishEvent(new SeriesData(JSONObject.toJSONString(objectObjectConcurrentMap),
                users));

        List<User> userList = userService.queryAllUsers();
        //分组
        Map<String, List<User>> collect = userList.stream().collect(Collectors.groupingBy(user -> user.getId() + "_" + user.getUsername()));
        return new APIResponse(collect);
    }
}

class Response {
    int affectRows;
    String message;

    public int getAffectRows() {
        return affectRows;
    }

    public void setAffectRows(int affectRows) {
        this.affectRows = affectRows;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

