package com.platform.aix.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 18:57
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 添加生产环境域名（关键修改）
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://pikai.cloud"  // 新增允许的线上域名
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                // 添加缓存时间（可选优化）
                .maxAge(3600); // 预检请求缓存时间（秒）
    }
}