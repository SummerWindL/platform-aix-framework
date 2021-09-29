package com.platform.aix.service.user.prj;


import com.platform.aix.service.user.prj.bean.TUserAccountPrj;

/**
 * 用户项目关联
 *
 * @author: Advance
 * @date: 2018/8/5
 * @description:
 */
public interface UserAccountPrjService {

    TUserAccountPrj[] queryUserAccountPrj(String userid);

    void saveUserAccountPrj(String userid, String projectid, String titleid);
}
