package com.platform.aix.service.user.account;

import com.platform.aix.cmd.biz.baseconf.cmd44110.*;
import com.platform.aix.service.user.account.bean.TUserAccount;
import com.platform.aix.service.user.account.bean.UserAccountFilter;
import com.platform.aix.service.user.role.rolegroup.bean.RoleGroupFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserAccountService {

    /**
     * 校验用户密码
     *
     * @param acc
     * @param pwd
     * @return
     */
    void checkUserPwd(String acc, String pwd);

    /**
     * 根据帐号查询帐号信息
     *
     * @param acc
     * @return
     */
    TUserAccount getDxUserAccount(String acc);

    /**
     * 根据userid查询帐号信息
     *
     * @param userid
     * @return
     */
    TUserAccount getUserAccountByUserid(String userid);

    // 获取全局帐号列表，权限太大了。。。
    // 还需要完善模糊查询功能支持
    List<TUserAccount> getDxUserAccountList(UserAccountFilter filter);


    /****
    * @Description: 查询所属机构下全部用户信息  ， enableflag =‘10000’ & sysflag =‘20000’
    * @attention: 该接口不应该提供分页，后期需要删除分页条件
    * @Param: [filter]
    * @return: java.util.List<com.ikinloop.aimb.management.cmd.bean.response.MbUserInfo>
    * @Author: fuyl
    * @Date: 2019/1/9
    */
    List<TUserAccount> getMbUserInfoList(UserAccountFilter filter);
    
    /*** 
    * @Description: 查询当前登录用户权限医院下医生列表
    * @Param: [filter] 
    * @return: java.util.List<com.ikinloop.aimb.management.service.zx.cfg.user.account.useraccount.bean.TUserAccount> 
    * @Author: fuyl 
    * @Date: 2019/4/15 
    */
    Page<TUserAccount> getMbAuthHospUserInfoList(UserAccountFilter filter);

    /**
     * 查询用户
     *
     * @param filter
     * @return
     */
    Page<TUserAccount> queryUserAccount(UserAccountFilter filter);

    /**
     * 删除用户
     *
     * @param tUserAccount
     * @return
     */
    void deleteUserAccount(TUserAccount tUserAccount);

    /**
     * 新增用户
     *
     * @param tUserAccount
     * @return
     */
    void insertUserAccount(TUserAccount tUserAccount);

    /**
     * 修改用户
     *
     * @param tUserAccount
     * @return
     */
    void updateUserAccount(TUserAccount tUserAccount);

    /**
     * 重置密码
     *
     * @param tUserAccount
     */
    void resetUserAccountPwd(TUserAccount tUserAccount);

    /**
     * 修改密码 在updateUserAccount的时候实现了
     * @param userid
     * @param oldPwd
     * @param newPwd
     */
    void modUserAccountPwd(String userid, String acc, String oldPwd, String newPwd);

    /**
     * 查询用户角色
     * @param roleGroupFilter
     * @return
     */
    Page<Cmd44110Resp> queryUserAccountRole(RoleGroupFilter roleGroupFilter);


    /**
     * 修改账号表医院权限标记
     *
     * @param
     * @return
     */
//    void updateUserAccountHospauthflag(MbUserAccountFilter mbUserAccountFilter);

    /**
     * 修改账号表科室权限标记
     *
     * @param
     * @return
     */
//    void updateUserAccountDeptauthflag(MbUserAccountFilter mbUserAccountFilter);

    /**
     * 修改账号表医生权限标记
     *
     * @param
     * @return
     */
//    void updateUserAccountDoctorauthflag(MbUserAccountFilter mbUserAccountFilter);

    /**
     * 修改账号表划区权限标记
     *
     * @param
     * @return
     */
//    void updateUserAccountAreaauthflag(MbUserAccountFilter mbUserAccountFilter);
}
