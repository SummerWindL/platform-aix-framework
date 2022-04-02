package com.platform.aix.common.datacommon.db.service;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.User;

import java.util.List;

/**
 * @author: Advance
 * @create: 2022-03-26 11:47
 * @since V1.0.0
 */
public interface UserService extends AsyncService<String, User> {

    List<User> queryByUserId(String userId);
}
