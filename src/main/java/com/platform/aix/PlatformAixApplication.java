package com.platform.aix;

import com.platform.aix.service.base.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:25
 **/
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.platform"})
public class PlatformAixApplication implements CommandLineRunner {

    @Resource
    private TaskExecutor taskExecutor;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor; // 注入线程池

    public static void main(String[] args) {
        SpringApplication.run(PlatformAixApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        //测试创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                doRequest();
            }
        });
        //注册线程池
        ThreadPoolExecutorShutdownDefinition.registryExecutor(executorService);
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doRequest();
            }
        });

        //注册线程池
        ThreadPoolExecutorShutdownDefinition.registryExecutor(threadPoolTaskExecutor);
        // 注入线程池
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(
                threadPoolTaskExecutor);
        completionService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                // insertList()方法 就是批量insert语句
                return "开始执行";
            }
        });


    }

    private void doRequest() {
        System.out.printf("发送XXX请求！");
    }
}
