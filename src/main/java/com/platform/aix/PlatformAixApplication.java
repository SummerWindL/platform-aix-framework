package com.platform.aix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 14:25
 **/
@SpringBootApplication(scanBasePackages = {"com.platform"})
public class PlatformAixApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformAixApplication.class);
    }
}
