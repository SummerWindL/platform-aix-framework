# platform-aix-framework
前后端分离 后台开发框架

## 后台接口

springboot + jetty + springdatajpa

若启动存在异常多半是包依赖的问题，去到对应的目录删除包重新maven update就好

可自行实现业务逻辑


e.g
 
![请求示例](https://github.com/SummerWindL/imgrepository/blob/master/platform-aix/application-json.png) 

请求地址（可配置）：
```http://localhost:16089/aixapigateway/api/v1/manageclient/cmd_10010factoryid=zav6f0t3w6fwz9u85remvqxkduo99tjj&factorysecretkey=3fpjfimcp2iybd6h4l30c1kpvjzfcipg```

数据请求采用Web  Post请求的方式,上传和回应数据为JSON格式，在接收页面处理上传的数据即可

application/json
application/x-www-form-urlencoded
两者都支持

请求JSON用例  
```json 
{
    "DoctorName":"医生名称",
    "MachineId":"HST821255555",
    "BloodPressure":{
        "HighPressure":"96",
        "Pulse":"65",
        "LowPressure":"57",
        "Result":""
    },
    "Temperature":{
        "Temperature":"37",
        "Result":"1"
    },
    "Chol":{
        "Chol":"2.77",
        "Result":"1"
    }
}
```

可使用 postman、Talend API Tester - Free Edition接口调试工具 调试


## Future
- [x] 1、基础调用
- [ ] 2、spring-boot版本升级
- [ ] 3、动态返回结构 目前只有IBaseHandler接口
- [x] 4、接口限流处理
- [ ] 5、请求权限控制
- [x] 6、数据库通知Java pg_notify 
---
>[在PostgreSQL触发器函数中使用pg_notify](http://www.voidcn.com/article/p-kpomgajq-bts.html)

>[官方Listen/Notify](https://jdbc.postgresql.org/documentation/81/listennotify.html) 
---

## Description
4、接口限流处理采用apo+redis+注解的方式实现，
在HandlerRequestRunnable拦截所有发送到后台的请求，判定请求是否包含Access（需要限流）的注解，若包含
进一步判断是否存在AccessLimit（需要拦截方法）的注解
第一次请求redis获取不到指定请求路径（Controller）的访问次数，此时默认赋1
往后根据请求次数+1 若最大请求次数大于限制请求次数，返回业务级别 请求过于频繁错误。

6、数据库通知模块，需要配合数据库允许测试用例使用，当启动服务，```PostgresNotificationImpl```启动线程开始监听
指定的目标，同时添加针对指定```notifyno```监听的处理器类,具体请阅读```PgNotifyMessageAccepter```
当数据库执行```pg_notify```函数命令
以下是本人针对pg_notify的简易封装 
>select * into v_rec from f_aix_notification_model('f_test_pg_notify','mymessage','{"notifyno":"cmd_4060001", "notifytype" :"update","notifyparam":{"disecode":"xxx","disename":"xxxx"}}')

像指定的目标传递指定的message时，message一定需要按照以下结构构造（当然可根据自己的实际业务场景进行重构）；
>{"notifyno":"cmd_4060001", "notifytype" :"update","notifyparam":{"disecode":"xxx","disename":"xxxx"}}

notifyno的value一定时addHandler的第一个参数，否则Java将无法收到消息。



--------------------------
登录用户密码：
admin
123456

------------------------------分割线-------------------------------
<br>2022年5月17日21:19:31 <br>
1、新增Disruptor模块，代码路径```src/main/java/com/platform/aix/service/processor/disruptor```

使用
```java
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
```

2、新增异步代码数据库、redis插入逻辑 代码路径：
src/main/java/com/platform/aix/common/datacommon
