package com.platform.aix.service.base;

import com.platform.aix.common.constants.DBSaveType;
import com.platform.aix.common.datacommon.cache.config.DataServiceConfig;
import com.platform.aix.common.datacommon.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Advance
 * @date 2022年03月26日 12:26
 * @since V1.0.0
 */
@Component
public class TransactionHelper {
    @Autowired
    private UserService userService;
    @Autowired
    private DataServiceConfig dataServiceConfig;

    public void save(){
        int dbSaveType = dataServiceConfig.isAsyncDb() ? DBSaveType.ASYNC.value : DBSaveType.SYNC_BATCH.value;
    }
}
