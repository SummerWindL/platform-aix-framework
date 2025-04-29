package com.platform.aix;

import cn.hutool.core.thread.ThreadUtil;
import com.platform.aix.service.base.ThreadPoolExecutorShutdownDefinition;
import com.platform.common.util.BeanUtil;
import com.platform.comservice.config.StarterService;
//import org.apache.ignite.Ignite;
//import org.apache.ignite.IgniteCache;
//import org.apache.ignite.cache.query.SqlFieldsQuery;
//import org.apache.ignite.configuration.DataRegionConfiguration;
//import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
//import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.AsyncRestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:25
 **/
@EnableScheduling
@SpringBootApplication(
        scanBasePackages = {"com.platform", "com.lemon"},
        scanBasePackageClasses = {StarterService.class}
)
@MapperScan("com.platform.aix.common.datacommon.db.dao")
public class PlatformAixApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(PlatformAixApplication.class);
    @Resource
    private TaskExecutor taskExecutor;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor; // 注入线程池
    @Resource(name = "aixDrawExecutor")
    private ExecutorService aixDrawExecutor;

    public static void main(String[] args) {
        SpringApplication.run(PlatformAixApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        //================测试线程池1 start================//
        // 测试创建一个线程池
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
        //================测试线程池1 end================//

        //================测试线程池2 start===============//
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
        //================测试线程池2 end===============//

        //================测试线程池3 start===============//
        //注册线程池
        ThreadPoolExecutorShutdownDefinition.registryExecutor(aixDrawExecutor);
        aixDrawExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doRequest();
            }
        });
        //================测试线程池3 end===============//

    }
    /**
     * Providing configurer for the Ignite.
     * @return Ignite Configurer.
     */
//    @Bean
//    public IgniteConfigurer configurer() {
//        return cfg -> {
//            //Setting consistent id.
//            //See `application.yml` for the additional properties.
//            cfg.setConsistentId("consistent-id");
//            cfg.setCommunicationSpi(new TcpCommunicationSpi());
//        };
//    }
//    /**
//     * Service using autoconfigured Ignite.
//     * @return Runner.
//     */
//    @Bean
//    public CommandLineRunner runner() {
//        return new CommandLineRunner() {
//            /** Ignite instance. */
//            @Autowired
//            private Ignite ignite;
//
//            /** Method will be executed on application startup. */
//            @Override public void run(String... args) throws Exception {
//                System.out.println("ServiceWithIgnite.run:");
//                //This property comes from configurer. See AutoConfigureExample.
//                System.out.println("    IgniteConsistentId: " + ignite.configuration().getConsistentId());
//
//                //Other properties are set via application-node.yml.
//                System.out.println("    IgniteInstanceName: " + ignite.configuration().getIgniteInstanceName());
//                System.out.println("    CommunicationSpi.localPort: " +
//                        ((TcpCommunicationSpi)ignite.configuration().getCommunicationSpi()).getLocalPort());
//                System.out.println("    DefaultDataRegion initial size: " +
//                        ignite.configuration().getDataStorageConfiguration().getDefaultDataRegionConfiguration().getInitialSize());
//
//                DataRegionConfiguration drc =
//                        ignite.configuration().getDataStorageConfiguration().getDataRegionConfigurations()[0];
//
//                System.out.println("    " + drc.getName() + " initial size: " + drc.getInitialSize());
//                System.out.println("    Cache in cluster:");
//
//                for (String cacheName : ignite.cacheNames())
//                    System.out.println("        " + cacheName);
//
//                cacheAPI();
//                sqlAPI();
//            }
//
//            /** Example of the SQL API usage. */
//            private void sqlAPI() {
//                //This cache configured in `application.yml`.
//                IgniteCache<Long, Object> accounts = ignite.cache("accounts");
//
//                //SQL table configured via QueryEntity in `application.yml`
//                String qry = "INSERT INTO ACCOUNTS(ID, AMOUNT, UPDATEDATE) VALUES(?, ?, ?)";
//
//                accounts.query(new SqlFieldsQuery(qry).setArgs(1, 250.05, new Date())).getAll();
//                accounts.query(new SqlFieldsQuery(qry).setArgs(2, 255.05, new Date())).getAll();
//                accounts.query(new SqlFieldsQuery(qry).setArgs(3, .05, new Date())).getAll();
//
//                qry = "SELECT * FROM ACCOUNTS";
//
//                List<List<?>> res = accounts.query(new SqlFieldsQuery(qry)).getAll();
//
//                for (List<?> row : res)
//                    System.out.println("(" + row.get(0) + ", " + row.get(1) + ", " + row.get(2) + ")");
//            }
//
//            /** Example of the Cache API usage. */
//            private void cacheAPI() {
//                //This cache configured in `application.yml`.
//                IgniteCache<Integer, Integer> cache = ignite.cache("my-cache2");
//
//                System.out.println("Putting data to the my-cache1...");
//
//                cache.put(1, 1);
//                cache.put(2, 2);
//                cache.put(3, 3);
//
//                System.out.println("Done putting data to the my-cache1...");
//            }
//        };
//    }


    private void doRequest() {
        logger.info("发送XXXX请求！");
    }
}
