package com.platform.aix;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:25
 **/
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.platform"})
public class PlatformAixApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PlatformAixApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("执行点什么！");
    }
}
