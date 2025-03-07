package com.platform.aix.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Advance
 * @date 2024年05月10日 10:02
 * @since V1.0.0
 */
//定义接口
interface Service{
    public void before();
    public void run();
    public void after();
}

class DoMain implements Service{
    public static volatile Service target = null;
    public DoMain(Service service){
        if (target == null){
            synchronized (DoMain.class){
                if (target == null) {
                    target = (Service) Proxy.newProxyInstance(
                            Service.class.getClassLoader(),new Class<?>[]{Service.class},
                            new MyInvocationHandler(service));
                }
            }
        }
    }
    public void start() {
        if (target != null) {
            target.run();
        }
    }

    @Override
    public void before() {}
    @Override
    public void run() {}
    @Override
    public void after() {}

}

//实现InvocationHandler接口
class MyInvocationHandler implements InvocationHandler {
    private Object target;
    public MyInvocationHandler(Object target) {
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method before = target.getClass().getMethod("before");
        before.invoke(target);
        Object result = method.invoke(target, args);
        Method after = target.getClass().getMethod("after");
        after.invoke(target);
        return result;
    }
}

public class Test{
    public static void main(String[] args) throws Exception {
        new DoMain(new Service() {
            @Override
            public void before() {
                System.out.println("before");
            }
            @Override
            public void run() {
                System.out.println("run");
            }
            @Override
            public void after() {
                System.out.println("after");
            }
        }).start();
    }
}
