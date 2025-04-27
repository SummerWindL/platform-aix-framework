package com.platform.aix.common;

import com.platform.aix.PlatformAixApplicationTests;
import com.platform.common.util.JasyptUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月25日 16:33
 */
public class JasyptEncryptionTest extends PlatformAixApplicationTests {

    // 密钥
    private static final String PASSWORD = "mySecretKey";

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void testBasicEncryption() {
        // 1. 创建加密器
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        // 删除不存在的 setIvGeneratorClassName 方法调用
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        // 2. 原始密码
        String originalPassword = "password123";

        // 3. 加密
        String encryptedPassword = encryptor.encrypt(originalPassword);
        System.out.println("加密后: " + encryptedPassword);

        // 4. 解密
        String decryptedPassword = encryptor.decrypt(encryptedPassword);
        System.out.println("解密后: " + decryptedPassword);

        // 5. 验证
        assertNotEquals(originalPassword, encryptedPassword);
        assertEquals(originalPassword, decryptedPassword);
    }

    @Test
    public void testSpringBootEncryption() {
        // 使用Spring Boot集成的加密器
        String originalText = "";

        // 加密
        String encryptedText = stringEncryptor.encrypt(originalText);
        System.out.println("Spring Boot加密后: " + encryptedText);
        System.out.println("可在application.properties中使用: ENC(" + encryptedText + ")");

        // 解密
        String decryptedText = stringEncryptor.decrypt(encryptedText);
        System.out.println("Spring Boot解密后: " + decryptedText);

        // 验证
        assertEquals(originalText, decryptedText);
    }

}
