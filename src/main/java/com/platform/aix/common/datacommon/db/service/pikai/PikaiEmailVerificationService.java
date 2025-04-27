package com.platform.aix.common.datacommon.db.service.pikai;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification;

import java.util.List;
import java.util.Optional;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月21日 13:59
 */
public interface PikaiEmailVerificationService extends AsyncService<String, PikaiEmailVerification> {
    /**
     * 根据userId和状态查询验证记录
     * @author fuyanliang
     * @date 2025/4/21 14:01
     * @param userId
     * @param status
     * @return java.util.List<com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification>
     */
    List<PikaiEmailVerification> findByUserIdAndStatusOrderByCreateTimeDesc(String userId, String status);
    /**
     * 根据验证码查询验证记录
     * @author fuyanliang
     * @date 2025/4/21 14:02
     * @param verificationCode
     * @return java.util.Optional<com.platform.aix.common.datacommon.db.domain.PikaiEmailVerification>
     */
    Optional<PikaiEmailVerification> findByVerificationCode(String verificationCode);


    /**
     * 发送验证邮件
     * @author fuyanliang
     * @date 2025/4/21 14:55
     * @param accountId
     * @return boolean
     */
    public boolean sendVerificationEmail(String accountId);
    /**
     * 校验邮箱
     * @author fuyanliang
     * @date 2025/4/21 15:15
     * @param verificationCode
     * @return boolean
     */
    boolean verifyEmail(String verificationCode);
}
