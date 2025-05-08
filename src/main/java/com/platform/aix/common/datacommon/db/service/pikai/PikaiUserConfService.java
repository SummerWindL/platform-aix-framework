package com.platform.aix.common.datacommon.db.service.pikai;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PikaiUserConf;
import com.platform.aix.controller.pikai.common.request.PikaiUserConfReq;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月08日 17:05
 */
public interface PikaiUserConfService extends AsyncService<Integer, PikaiUserConf> {
    int doSave(PikaiUserConfReq conf);
    void update(PikaiUserConf conf);
    PikaiUserConf getPikaiUserConf(String userId,String confItemCode);
}
