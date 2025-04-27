package com.platform.aix.common.datacommon.db.service.pikai.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiEmailVerificationMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiEmailVerificationService;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.common.exception.AuthenticationException;
import com.platform.aix.common.util.DateUtil;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.service.pikai.MailtrapEmailService;
import com.platform.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月21日 14:04
 */
@Service
public class PikaiEmailVerificationServiceImpl  extends AsyncServiceImpl<String, PikaiEmailVerification> implements PikaiEmailVerificationService {

    @Autowired
    private PikaiEmailVerificationMapper pikaiEmailVerificationMapper;
    @Autowired
    private PikaiUserService pikaiUserService;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${verification.email.expire.hours:24}")
    private int expirationHours;
    @Autowired
    private MailtrapEmailService mailtrapEmailService;
    @Override
    public IMybatisMapper<PikaiEmailVerification, String, ?> getMapper() {
        return this.pikaiEmailVerificationMapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PikaiEmailVerification pikaiEmailVerification) {

    }

    @Override
    protected void save(PikaiEmailVerification pikaiEmailVerification) {
        pikaiEmailVerificationMapper.updateOrInsertSelective(pikaiEmailVerification);
    }

    @Override
    protected void delete(String PK) {

    }

    @Override
    public List<PikaiEmailVerification> findByUserIdAndStatusOrderByCreateTimeDesc(String userId, String status) {
        return pikaiEmailVerificationMapper.findByUserIdAndStatusOrderByCreateTimeDesc( userId,  status);
    }

    @Override
    public Optional<PikaiEmailVerification> findByVerificationCode(String verificationCode) {
        return pikaiEmailVerificationMapper.findByVerificationCode(verificationCode)/*
                .orElseThrow(() -> new BusinessException(ResponseCode.EMAIL_VERIFICATIONCODE_NOTEXISTS.getCode(),"邮箱验证码不存在"))*/;
    }

    public boolean sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("humans@pikai.dev");

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendVerificationEmail(String accountId) {
        // 通过账户ID（邮箱）查找用户
        PikaiUser user = pikaiUserService.findByAccountId(accountId);
        if(ObjectUtil.isEmpty(user)){
            throw new BusinessException(ResponseCode.USER_NOT_FOUND.getCode(),"用户不存在");
        }
        // 创建新的验证记录
        PikaiEmailVerification verification = new PikaiEmailVerification();
        verification.setVerificationId(UUIDUtils.getUUID());
        verification.setUserId(user.getUserId());
        verification.setEmail(accountId);
        verification.setVerificationCode(generateVerificationCode());
        verification.setStatus("PENDING");
        // 获取当前时间 +24 小时的 LocalDateTime
        LocalDateTime futureLocalDateTime = LocalDateTime.now().plusHours(24);

        // 转换为 Date（需指定时区）
        Date futureDate = Date.from(
                futureLocalDateTime
                        .atZone(ZoneId.systemDefault()) // 使用系统默认时区
                        .toInstant()
        );
        verification.setExpireTime(futureDate);
        verification.setCreateTime(DateUtil.now());
        verification.setUpdateTime(DateUtil.now());

        // 保存验证记录到数据库
        save(verification);

        // 发送验证邮件
        String subject = "Verify Email Address";
        String body = "请点击以下链接验证您的邮箱：\n" +
                "http://your-app-domain.com/verify?code=" + verification.getVerificationCode();
        // 以下方法只支持JDK17
//        return mailtrapEmailService.sendMailTrapTemplate(accountId,verification.getVerificationCode());
        return mailtrapEmailService.sendEmailCode(accountId,verification.getVerificationCode());
//        return sendEmail(accountId, subject, body);
    }

    @Override
    public boolean verifyEmail(String verificationCode) {
        Optional<PikaiEmailVerification> verificationOpt = pikaiEmailVerificationMapper.findByVerificationCode(verificationCode);

        if (!verificationOpt.isPresent()) {
            return false;
        }

        PikaiEmailVerification verification = verificationOpt.get();

        // 检查验证是否过期
        if (verification.getExpireTime().before(DateUtil.now())) {
            verification.setStatus("EXPIRED");
            save(verification);
            return false;
        }

        // 检查是否已验证
        if ("VERIFIED".equals(verification.getStatus())) {
            return true;
        }

        // 更新验证状态
        verification.setStatus("VERIFIED");
        verification.setUpdateTime(DateUtil.now());
        save(verification);

        return true;
    }
    private String generateVerificationCode() {
        return UUIDUtils.getUUID();
    }
}
