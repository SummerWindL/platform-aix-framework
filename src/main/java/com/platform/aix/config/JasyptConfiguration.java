package com.platform.aix.config;

import com.platform.common.util.StringUtil;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;

/**
 * @author Advance
 * @date 2023年01月04日 14:35
 * @since V1.0.0
 */
@EnableEncryptableProperties
public class JasyptConfiguration {
    @Bean("stringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("joyin");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(getJce());
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    /**
     * 自动获取jce类型
     * @return jce
     */
    private String getJce() {
        String vendor = System.getProperty("java.vm.vendor");
        if(StringUtil.containsIgnoreCase(vendor, "IBM")) {
            return "IBMJCE";
        }
        return "SunJCE";
    }
}
