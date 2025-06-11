package com.platform.aix.common.util;

import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月11日 14:04
 */
public class SecurityUtil {

    public static String generateJwtToken(PikaiUser user) {
        long expireTime = System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME;

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("accountId", user.getAccountId())
                .claim("userName", user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET)
                .compact();
    }

    public static String generateJwtToken(String originalToken) {
        long expireTime = System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME;
        // 1. 获取拼接的字符串（假设originalToken是拼接后的字符串）
        // 这里直接用拼接结果作为JWT主题
        String subject = originalToken;

        // 4. 生成JWT
        return Jwts.builder()
                // 将拼接字符串放入Subject
                .setSubject(subject)
                // 签发时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(expireTime))
                // 签名密钥
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET)
                // 生成字符串
                .compact();
    }
}
