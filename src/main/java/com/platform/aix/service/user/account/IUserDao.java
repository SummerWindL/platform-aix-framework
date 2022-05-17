package com.platform.aix.service.user.account;

import com.platform.aix.common.agent.Select;

public interface IUserDao {

    @Select("select userName from user where id = #{uId}")
    String queryUserInfo(String uId);
}
