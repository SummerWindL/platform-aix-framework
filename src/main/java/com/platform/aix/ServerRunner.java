package com.platform.aix;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.platform.aix.common.handler.HttpHandler;
import com.platform.aix.config.ApisPorperties;
import com.platform.aix.config.ApisServer;
import com.platform.aix.service.base.ServiceThreadManagerRunner;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jetty.server.Server;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Order(value = 1)
public class ServerRunner implements ApplicationRunner {

    //private final static Logger LOGGER = LoggerFactory.getLogger(ServerRunner.class);
    private final Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    private ApisPorperties apisPorperties;
    @Autowired
    private ServiceThreadManagerRunner serviceThreadManagerRunner;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        LOGGER.info("\n--------启动服务 START--------");

        ApisServer apisServer = apisPorperties.getServer();
        int port = apisServer.getPort();
        int threadCount = apisServer.getThreadCount();

        Server server = new Server(port);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", 1000000);
        server.setHandler(new HttpHandler(executorService));
        try {
            server.start();
            serviceThreadManagerRunner.submit();
            LOGGER.info("--------服务启动成功 Listen: {}, Thread count: {} -------- ", port, threadCount);

            //server.join();
        } catch (Exception e) {
            LOGGER.error("Platform AIX Server start failed!Exception: ", e);
        }

        //LOGGER.info("Server stop success！");
    }



}