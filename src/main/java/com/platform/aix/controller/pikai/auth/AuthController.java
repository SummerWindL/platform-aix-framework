package com.platform.aix.controller.pikai.auth;

import com.cluster.platform.redis.ICache;
import com.google.code.kaptcha.Producer;
import com.google.common.cache.Cache;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.domain.dict.DictItem;
import com.platform.aix.common.datacommon.db.domain.dict.DictUtil;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiEmailVerificationService;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.common.util.SecurityUtil;
import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.AuthResponse;
import com.platform.aix.controller.pikai.common.LoginRequest;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.service.pikai.gateway.dto.WeixinTokenResponseDTO;
import com.platform.aix.service.pikai.weixin.ILoginService;
import com.platform.aix.service.pikai.weixin.IWeixinApiService;
import com.platform.aix.service.pikai.weixin.IWeixinMPService;
import com.platform.common.util.DateUtil;
import com.platform.common.util.UUIDUtils;
import com.platform.core.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;

import java.util.concurrent.TimeUnit;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 16:52
 */
@RestController
@RequestMapping("/pikai/api/auth")
@RequiredArgsConstructor
public class AuthController{

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PikaiUserService pikaiUserService;
    @Autowired
    private PikaiEmailVerificationService pikaiEmailVerificationService;
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private ICache cache;

    @Resource
    private ILoginService loginService;
    @Resource
    private IWeixinMPService weixinMPService;

    /**
     * 获取微信 ticket 凭证
     * <a href="http://xfg-studio.natapp1.cc/api/v1/login/weixin_qrcode_ticket">/api/v1/login/weixin_qrcode_ticket</a>
     */
    @RequestMapping(value = "weixin_qrcode_ticket", method = RequestMethod.GET)
    public ApiResponse<String> weixinQrCodeTicket() {
        try {
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成微信扫码登录 ticket {}", qrCodeTicket);
            return ApiResponse.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(qrCodeTicket)
                    .build();
        } catch (Exception e) {
            log.info("生成微信扫码登录 ticket 失败", e);
            return ApiResponse.<String>builder()
                    .code(ResponseCode.INTERNAL_ERROR.getCode())
                    .message(ResponseCode.INTERNAL_ERROR.getMessage())
                    .build();
        }
    }

    // 获取微信凭证并生成二维码
    /**
     * 聚合接口
     * @author fuyanliang
     * @date 2025/6/10 18:50
     * @return com.platform.aix.controller.pikai.common.ApiResponse<java.lang.Object>
     */
    @RequestMapping(value = "wechat/qrcode", method = RequestMethod.GET)
    public ApiResponse<Map<String,Object>> getQrCode() {
        try {
            String qrCodeTicket = loginService.createQrCodeTicket();
            log.info("生成微信扫码登录 ticket {}", qrCodeTicket);
            HashMap result = new HashMap();
            result.put("ticket",qrCodeTicket);
            result.put("qrCode",weixinMPService.getQrCode(qrCodeTicket));
            return ApiResponse.<Map<String,Object>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message(ResponseCode.SUCCESS.getMessage())
                    .data(result)
                    .build();
        } catch (Exception e) {
            log.info("生成微信扫码登录 ticket 失败", e);
            return ApiResponse.<Map<String,Object>>builder()
                    .code(ResponseCode.INTERNAL_ERROR.getCode())
                    .message(ResponseCode.INTERNAL_ERROR.getMessage())
                    .build();
        }
    }

    /**
     * 轮训登录
     * <a href="http://xfg-studio.natapp1.cc/api/v1/login/check_login">/api/v1/login/check_login</a>
     */
    @RequestMapping(value = "wechat/check_login", method = RequestMethod.POST)
    public ApiResponse<Map<String,String>> checkLogin(@RequestBody String ticket) {
        try {
            String openidToken = loginService.checkLogin(ticket);
            log.info("扫描检测登录结果 ticket:{} openidToken:{}", ticket, openidToken);
            if (StringUtils.isNotBlank(openidToken)) {
                // 返回token到前端
                return ApiResponse.<Map<String,String>>builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .message(ResponseCode.SUCCESS.getMessage())
                        .data(loginService.accessLoginData(ticket))
                        .build();
            } else {
                return ApiResponse.<Map<String,String>>builder()
                        .code(ResponseCode.NO_LOGIN.getCode())
                        .message(ResponseCode.NO_LOGIN.getMessage())
                        .build();
            }
            // 测代码，模拟扫码成功
//            Map<String,String> userMap = new HashMap<>();
//            userMap.put("userId","9538984493df4340a32afc6b0de67787");
//            userMap.put("token",SecurityUtil.generateJwtToken("1111"));
//            return ApiResponse.<Map<String,String>>builder()
//                        .code(ResponseCode.SUCCESS.getCode())
//                        .message(ResponseCode.SUCCESS.getMessage())
//                        .data(userMap)
//                        .build();
        } catch (Exception e) {
            log.info("扫描检测登录结果失败 ticket:{}", ticket);
            return ApiResponse.<Map<String,String>>builder()
                    .code(ResponseCode.INTERNAL_ERROR.getCode())
                    .message(ResponseCode.INTERNAL_ERROR.getMessage())
                    .build();
        }
    }

    @PostMapping("register")
    public ApiResponse<String> registerUser(@RequestBody LoginRequest registerRequest) {
        // 0. 校验验证码
        String redisKey = "captcha:" + registerRequest.getUuid();
        String expectedCaptcha = cache.getOpsValues(redisKey);

        if (expectedCaptcha == null ||
                !expectedCaptcha.equalsIgnoreCase(registerRequest.getCaptcha())) {
            throw new BusinessException(ResponseCode.VERIFICATION_CODE_ERROR.getCode(), "验证码错误或已过期");
        }

        // 可选：验证成功后删除 Redis 中验证码
        cache.del(redisKey);
        // 1. 检查邮箱是否已存在
        if (pikaiUserService.existsByAccountId(registerRequest.getEmail())) {
            throw new BusinessException(ResponseCode.EMAIL_EXISTS.getCode(), "该邮箱已被注册");
        }

        // 5. 保存用户
        PikaiUser savedUser = pikaiUserService.doSave(registerRequest);

        // 6. 返回成功响应
        return ApiResponse.success(savedUser.getUserId());
    }


    @PostMapping("login")
    public ApiResponse<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        // 1. 根据邮箱查找用户
        PikaiUser user = pikaiUserService.findByAccountId(loginRequest.getEmail());
        if (user == null) {
            throw new BusinessException(ResponseCode.USER_NOT_FOUND.getCode(), "用户不存在");
        }

        // 2. 检查账户状态
        if (!"0".equals(user.getAccountStatus())) {
            throw new BusinessException(ResponseCode.ACCOUNT_DISABLED.getCode(), "账户已被禁用");
        }

        // 检查邮箱是否通过验证
        DictItem item = DictUtil.getItem("EMAIL_VERIFICATION_STATUS", "VERIFIED");
        List<PikaiEmailVerification> byUserIdAndStatusOrderByCreateTimeDesc = pikaiEmailVerificationService.findByUserIdAndStatusOrderByCreateTimeDesc(user.getUserId(), item.getItemCode());
        if (CollectionUtils.isEmpty(byUserIdAndStatusOrderByCreateTimeDesc)|| !StringUtil.equals("VERIFIED",byUserIdAndStatusOrderByCreateTimeDesc.stream().findFirst().get().getStatus())) {
            throw new BusinessException(ResponseCode.EMAIL_VERIFICATION_NOT_PASS.getCode(), "未通过邮箱检验");
        }

        // 3. 验证密码
        boolean isPasswordValid = validatePassword(
                loginRequest.getPassword(),
                user.getPassword(),
                user.getSalt(),
                user.getEncrypType()
        );

        if (!isPasswordValid) {
            throw new BusinessException(ResponseCode.INVALID_CREDENTIALS.getCode(), "密码错误");
        }

        // 4. 更新最后登录时间
//        user.setLastLoginTime(DateUtil.now());
//        pikaiUserService.doUpdate(user);

        // 5. 返回成功响应和用户ID
        // 调整返回格式，确保包含 Next-Auth 所需信息
        String token = SecurityUtil.generateJwtToken(user);
        AuthResponse response = new AuthResponse();
        response.setUserId(user.getUserId());
        response.setToken(token);                       // JWT token
        response.setEmail(user.getAccountId());         // 用户邮箱
        response.setUserName(user.getUserName());           // 用户名
        response.setExpireTime(System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME);

        log.info("用户 {} 登录成功！",loginRequest.getEmail());
        return ApiResponse.success(response);
    }

    @GetMapping("/captcha")
    public ApiResponse<Map<String, String>> getCaptcha(HttpSession session) {
        // 生成验证码文本
        String captchaText = kaptchaProducer.createText();

        // 将验证码保存到session中，用于后续验证
        session.setAttribute("captchaCode", captchaText);

        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaText);

        // 将图片转换为Base64编码
        String base64Image;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("exception : {} {}",e.getMessage(),e);
            throw new BusinessException(ResponseCode.INTERNAL_ERROR.getCode(),"服务器内部错误");
        }


        // 存入 redis
        String uuid = UUIDUtils.getUUID();
        String redisKey = "captcha:" + uuid;
        cache.putOpsValueString(redisKey, captchaText.toString().toLowerCase(), 5L, TimeUnit.MINUTES);

        // 4. 返回验证码图片和 UUID
        Map<String, String> result = new HashMap<>();
        result.put("image", "data:image/jpeg;base64," + base64Image);
        result.put("uuid", uuid);
        return ApiResponse.success(result);
    }

    @GetMapping("session")
    public ApiResponse<PikaiUser> validateSession(@RequestHeader("Authorization") String token) {
        // 验证token有效性
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED.getCode(), "无效的认证令牌");
        }

        String jwt = token.substring(7);
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_SECRET)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED.getCode(), "认证令牌已过期或无效");
        }

        // 返回用户信息
        PikaiUser userInfo = new PikaiUser();
        userInfo.setId(claims.getSubject());
        userInfo.setAccountId((String) claims.get("accountId"));
        userInfo.setUserId((String) claims.get("userId"));
        userInfo.setUserName((String) claims.get("userName"));

        return ApiResponse.success(userInfo);
    }

    /**
     * 刷新token
     * @author fuyanliang
     * @date 2025/4/18 11:20
     * @param token
     * @return com.platform.aix.controller.pikai.common.ApiResponse<com.platform.aix.controller.pikai.common.AuthResponse>
     */
    @PostMapping("refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestHeader("Authorization") String token) {
        // 验证旧token (即使过期也可以读取信息)
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED.getCode(), "无效的认证令牌");
        }

        String jwt = token.substring(7);
        Claims claims;

        try {
            // 允许过期的token解析，只取出用户ID
            claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_SECRET)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 即使token过期，我们仍然可以从异常中获取Claims
            claims = e.getClaims();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED.getCode(), "认证令牌无效");
        }

        String userId = claims.getSubject();
        PikaiUser user = pikaiUserService.findByAccountId(userId);

        if (user == null || !"0".equals(user.getAccountStatus())) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED.getCode(), "用户不存在或已禁用");
        }

        // 生成新token
        String newToken = SecurityUtil.generateJwtToken(user);

        // 返回新token
        AuthResponse response = new AuthResponse();
        response.setUserId(user.getUserId());
        response.setToken(newToken);
        response.setEmail(user.getAccountId());
        response.setUserName(user.getUserName());
        response.setExpireTime(System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME);

        return ApiResponse.success(response);
    }

    private boolean validatePassword(String rawPassword, String storedPassword, String salt, String encrypType) {
        if ("BCRYPT".equals(encrypType)) {
            return passwordEncoder.matches(rawPassword + salt, storedPassword);
        }
        throw new UnsupportedOperationException("不支持的加密类型: " + encrypType);
    }


}
