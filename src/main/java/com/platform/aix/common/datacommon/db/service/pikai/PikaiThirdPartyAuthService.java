package com.platform.aix.common.datacommon.db.service.pikai;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年06月11日 11:51
 * 三方登录用户接口
 */
public interface PikaiThirdPartyAuthService extends AsyncService<String, PikaiThirdPartyAuth> {
    /**
     * 保存三方登录账号
     * @author fuyanliang
     * @date 2025/6/11 11:53
     * @param user
     * @param openId
     * @return com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth
     */
    PikaiThirdPartyAuth saveThirdPartyAuth(PikaiUser user,String openId);

    /**
     * 根据三方唯一ID查询userID
     * @author fuyanliang
     * @date 2025/6/11 13:58
     * @param openId
     * @return com.platform.aix.common.datacommon.db.domain.PikaiThirdPartyAuth
     */
    PikaiThirdPartyAuth selectOne(String openId);

}
