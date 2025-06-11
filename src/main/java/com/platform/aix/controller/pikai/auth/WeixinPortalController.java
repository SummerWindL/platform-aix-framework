package com.platform.aix.controller.pikai.auth;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiThirdPartyAuthService;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.common.util.weixin.MessageTextEntity;
import com.platform.aix.common.util.weixin.SignatureUtil;
import com.platform.aix.common.util.weixin.XmlUtil;
import com.platform.aix.controller.pikai.common.AuthResponse;
import com.platform.aix.controller.pikai.common.LoginRequest;
import com.platform.common.util.DateUtil;
import com.platform.common.util.UUIDUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月10日 14:38
 */
@Slf4j
@RestController()
//@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {
    private static final long TOKEN_EXPIRE_TIME = 86400000; // 24小时
    private static final String JWT_SECRET = "pikai-auth";
    @Value("${weixin.config.originalid}")
    private String originalid;
    @Resource
    private Cache<String, String> openidToken;
    @Autowired
    private PikaiUserService pikaiUserService;
    @Resource
    private PikaiThirdPartyAuthService pikaiThirdPartyAuthService;

    /**
     * 验签，硬编码 token ae86 - 按需修改
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check("ae86", signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    /**
     * 回调，接收公众号消息【扫描登录，会接收到消息】
     */
    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);

            // 扫码登录【消息类型和事件】
            if ("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())) {
                PikaiThirdPartyAuth thirdPartyAuth = pikaiThirdPartyAuthService.selectOne(openid);
                if(ObjectUtil.isEmpty(thirdPartyAuth)){
                    // 构造user对象 登录user表
                    LoginRequest user = new LoginRequest();
                    user.setPassword("888888");
                    user.setEmail(openid);
                    // 5. 保存用户
                    PikaiUser savedUser = pikaiUserService.doSave(user);
                    log.info("用户登录成功 {} ",openid);
                    log.info("====开始登录第三方登录授权信息表====");
                    PikaiThirdPartyAuth pikaiThirdPartyAuth = pikaiThirdPartyAuthService.saveThirdPartyAuth(savedUser, openid);
                    log.info("====登录第三方登录授权信息表成功====");
                    openidToken.put("token",pikaiThirdPartyAuth.getAccessToken());
                }else{
                    // 更新token
                    PikaiUser pikaiUser = pikaiUserService.selectOne(thirdPartyAuth.getUserId());
                    String refreshToken = generateJwtToken(pikaiUser);
                    thirdPartyAuth.setRefreshToken(refreshToken);
                    pikaiThirdPartyAuthService.asyncUpdate(thirdPartyAuth);
                    openidToken.put("token",refreshToken);
                }
                openidToken.put(message.getTicket(), openid);
                log.info("写入内存数据---> {}", JSON.toJSONString(openidToken));
                return buildMessageTextEntity(openid, "登录成功");
            }

            log.info("接收微信公众号信息请求{}完成 {}", openid, requestBody);
            return buildMessageTextEntity(openid, "测试本案例，需要请扫码登录！");
        } catch (Exception e) {
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }
    private String generateJwtToken(PikaiUser user) {
        long expireTime = System.currentTimeMillis() + TOKEN_EXPIRE_TIME;

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("accountId", user.getAccountId())
                .claim("userName", user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expireTime))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
