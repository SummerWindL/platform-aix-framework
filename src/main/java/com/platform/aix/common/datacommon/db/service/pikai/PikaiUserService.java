package com.platform.aix.common.datacommon.db.service.pikai;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.controller.pikai.common.request.PikaiPasswordReq;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 16:15
 */
public interface PikaiUserService extends AsyncService<String, PikaiUser> {
    PikaiUser doSave(PikaiUser user);
    /**
     * 更新
     * @author fuyanliang
     * @date 2025/4/18 10:55
     * @param user
     */
    void doUpdate(PikaiUser user);
    /**
     * 根据id 查询账户信息
     * @author fuyanliang
     * @date 2025/4/17 16:16
     * @param email
     * @return com.platform.aix.common.datacommon.db.domain.PikaiUser
     */
    PikaiUser findByAccountId(String email);

    /**
     * 账号是否存在
     * @author fuyanliang
     * @date 2025/4/17 17:48
     * @param email
     * @return boolean
     */
    boolean existsByAccountId(String email);

    PikaiUser selectOne(String userId);

    /**
     * 更新密码
     * @author fuyanliang
     * @date 2025/5/20 18:25
     * @param pikaiPasswordReq
     */
    void updatePassword(PikaiPasswordReq pikaiPasswordReq);

}
