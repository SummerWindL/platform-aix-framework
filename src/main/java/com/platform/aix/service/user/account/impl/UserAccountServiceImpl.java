package com.platform.aix.service.user.account.impl;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.account.UserAccountService;
import com.platform.aix.service.user.account.bean.TUserAccount;
import com.platform.aix.service.user.account.bean.UserAccountFilter;
import com.platform.aix.service.user.role.rolegroup.bean.RoleGroupFilter;
import com.platform.common.enumeration.EnumCountflagType;
import com.platform.common.util.UUIDUtils;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.AuthHospUserAccountListQueryRepository;
import com.platform.repo.pg.repo.user.*;
import com.platform.repo.pg.repo.user.account.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.platform.aix.cmd.biz.baseconf.cmd44110.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @date: 2018/4/19
 * @description:
 */
@Slf4j
@Service
public class UserAccountServiceImpl extends AbstractServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository mbUserAccountRepository;

    @Autowired
    private UserAccountListQueryRepository fMbUserAccountListQueryRepository;

    @Autowired
    private AuthHospUserAccountListQueryRepository fMbAuthHospUserAccountListQueryRepository;

    @Autowired
    private UserAccountQueryRepository fMbUserAccountQueryRepository;
    @Autowired
    private UserAccountInsertRepository fMbUserAccountInsertRepository;
    @Autowired
    private UserAccountUpdateRepository fMbUserAccountUpdateRepository;
    @Autowired
    private UserAccountDeleteRepository fMbUserAccountDeleteRepository;

    @Autowired
    private UserAccountRoleQueryRepository fMbUserAccountRoleQueryRepository;

    /*@Autowired
    private FMbUserAccountHospauthflagUpdateRepository fMbUserAccountHospauthflagUpdateRepository;
    @Autowired
    private FMbUserAccountDeptauthflagUpdateRepository fMbUserAccountDeptauthflagUpdateRepository;
    @Autowired
    private FMbUserAccountDoctorauthflagUpdateRepository fMbUserAccountDoctorauthflagUpdateRepository;
    @Autowired
    private FMbUserAccountAreaauthflagUpdateRepository fMbUserAccountAreaauthflagUpdateRepository;*/

    @Override
    public void checkUserPwd(String acc, String pwd) {
        BasePlpgsqlModel basePlpgsqlModel = this.mbUserAccountRepository.check(acc, pwd);
        int retCode = basePlpgsqlModel.getRetcode();

        ResponseResult rr = ResponseResult.COMMON_SUCCESS;
        if (retCode == -4) {
            rr = ResponseResult.BIZ_ERROR_USER_PWD_WRONG;
        }

        if (rr != ResponseResult.COMMON_SUCCESS) {
            throw new BIZException(rr);
        }
    }

    @Override
    public TUserAccount getDxUserAccount(String acc) {

        if (StringUtils.isEmpty(acc)) {
            // todo : 提示参数错误
            return null;
        }

        UserAccountFilter filter = new UserAccountFilter();
        filter.setAcc(acc);
        filter.setCountflag(EnumCountflagType.LIST.getCountflag());
        List<TUserAccount> list = getDxUserAccountList(filter);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 根据userid查询帐号信息
     *
     * @param userid
     * @return
     */
    @Override
    public TUserAccount getUserAccountByUserid(String userid) {
        if (StringUtils.isEmpty(userid)) {
            // todo : 提示参数错误
            return null;
        }

        UserAccountFilter filter = new UserAccountFilter();
        filter.setUserid(userid);
        filter.setCountflag(EnumCountflagType.LIST.getCountflag());
        List<TUserAccount> list = getDxUserAccountList(filter);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<TUserAccount> getDxUserAccountList(UserAccountFilter filter) {

        BasePlpgsqlModel basePlpgsqlModel = this.fMbUserAccountQueryRepository.fMbUserAccountQuery(filter.getAcc(), filter.getUserid(),"",
                filter.getCountflag(), filter.getOffset(), filter.getPagesize());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        List<TUserAccount> list = new ArrayList<>();
        TUserAccount[] userAccountArray = basePlpgsqlModel2Clz(basePlpgsqlModel, TUserAccount[].class);
        if (null != userAccountArray) {
            return CollectionUtils.arrayToList(userAccountArray);
        }

        return Collections.EMPTY_LIST;
    }

    /****
     * @Description: 查询所属机构下全部用户信息  ， enableflag =‘10000’ & sysflag =‘20000’
     * @Param: [filter]
     * @return: java.util.List<com.ikinloop.aimb.management.cmd.bean.response.MbUserInfo>
     * @Author: fuyl
     * @Date: 2019/1/9
     * @param filter
     */
    @Override
    public List<TUserAccount> getMbUserInfoList(UserAccountFilter filter) {

        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountListQueryRepository.fMbUserAccountListQuery(filter.getUserid(),
                filter.getHospcode(),
                2,
                0,
                1000);
        if(basePlpgsqlModel.getRetcode()<=0){
            throw new BIZException(ResponseResult.DB_ERROR_RECORD_NOTEXIST);
        }

        basePlpgsqlModel = fMbUserAccountListQueryRepository.fMbUserAccountListQuery(filter.getUserid(),
                filter.getHospcode(),
                3,
                0,
                99999);

        TUserAccount[] mbUserInfoArray = basePlpgsqlModel2Clz(basePlpgsqlModel, TUserAccount[].class);
        if (null != mbUserInfoArray) {
            return CollectionUtils.arrayToList(mbUserInfoArray);
        }

        return Collections.EMPTY_LIST;
    }

    /***
     * @Description: 查询当前登录用户权限医院下医生列表
     * @Param: [filter]
     * @return: java.util.List<com.ikinloop.aimb.management.service.zx.cfg.user.account.useraccount.bean.TUserAccount>
     * @Author: fuyl
     * @Date: 2019/4/15
     * @param filter
     */
    @Override
    public Page<TUserAccount> getMbAuthHospUserInfoList(UserAccountFilter filter) {

        BasePlpgsqlModel basePlpgsqlModel = fMbAuthHospUserAccountListQueryRepository.fMbAuthHospUserAccountListQuery(filter.getUserid(),
                filter.getHospcode(),
                filter.getDoctoruserid(),
                filter.getDeptcode(),
                filter.getHospname(),
                filter.getDoctorname(),
                filter.getDeptname(),
                filter.getCountflag(),
                filter.getOffset(),
                filter.getPagesize());
        Page<TUserAccount> tUserAccounts = paginationContent(basePlpgsqlModel, TUserAccount.class);

        if(basePlpgsqlModel.getRetcode()<0){
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        return tUserAccounts;
    }

    /**
     * 查询用户
     *
     * @param filter
     * @return
     */
    @Override
    public Page<TUserAccount> queryUserAccount(UserAccountFilter filter) {

        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountQueryRepository.fMbUserAccountQuery(
                filter.getAcc(),
                filter.getUserid(),
                filter.getHospcode(),
                filter.getCountflag(),
                filter.getOffset(),
                filter.getPagesize()
        );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        TUserAccount[] userAccountArray = basePlpgsqlModel2Clz(basePlpgsqlModel, TUserAccount[].class);

        if (null != userAccountArray) {
            List<TUserAccount> tUserAccounts = CollectionUtils.arrayToList(userAccountArray);
            Page<TUserAccount> pageList = new PageImpl<TUserAccount>(tUserAccounts, null, (long) basePlpgsqlModel.getRetcode());
            return pageList;
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param tUserAccount
     * @return
     */
    @Override
    public void deleteUserAccount(TUserAccount tUserAccount) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountDeleteRepository.fMbUserAccountDelete(
                tUserAccount.getUserid(),
                tUserAccount.getAcc()
        );

        if(0<=basePlpgsqlModel.getRetcode()){
            log.info(String.format("%s删除成功！", tUserAccount.getAcc()));
        }else if(-3==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_AND_HA_PK);
        }else{
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    /**
     * 新增用户
     * @param tUserAccount
     * @return
     */
    @Override
    public void insertUserAccount(TUserAccount tUserAccount) {
        String salt = UUIDUtils.getUUID();
        String pwd = DigestUtils.md5Hex(tUserAccount.getPwd() + salt);
        String acc = UUIDUtils.getUUID(tUserAccount.getAcc());

        /*BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountInsertRepository.fMbUserAccountInsert(
                tUserAccount.getUserid(),
                tUserAccount.getHospcode(),
                acc,
                pwd,
                salt,
                tUserAccount.getSysflag()
        );

        if(0 == basePlpgsqlModel.getRetcode()){
            log.info(String.format("%s新增成功！", tUserAccount.getAcc()));
        }else if(-1 == basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.DB_ERROR_RECORD_EXIST);
        }else if(-2 == basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_EXIST);
        }else if(-6 == basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_IS_NO_NULL);
        }else if(-3 == basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_PHONE_EXIST);
        }else{
            throw new BIZException(ResponseResult.DB_ERROR);
        }*/
    }

    /**
     * 修改用户
     *
     * @param tUserAccount
     * @return
     */
    @Override
    public void updateUserAccount(TUserAccount tUserAccount) {
        String userid = tUserAccount.getUserid();
//        String pwd = tUserAccount.getPwd();
//        String salt = "";
//
//        if (!StringUtils.isEmpty(pwd)) {
//            salt = UUIDUtils.getUUID();
//            pwd = DigestUtils.md5Hex(pwd + salt);
//        }

        /*BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountUpdateRepository.fMbUserAccountUpdate(
                userid,
                tUserAccount.getAcc(),
                tUserAccount.getPwd(),
                tUserAccount.getSalt(),
                tUserAccount.getEnableflag(),
                tUserAccount.getHospcode(),
                tUserAccount.getDeptcode(),
                tUserAccount.getDoctorlevelcode(),
                tUserAccount.getDoctorlevelname(),
                tUserAccount.getWorkdepartment(),
                tUserAccount.getIdcardno(),
                tUserAccount.getPhone(),
                tUserAccount.getWorkno(),
                tUserAccount.getSignature(),
                tUserAccount.getResume(),
                tUserAccount.getDoctorname(),
                tUserAccount.getGendercode(),
                tUserAccount.getGendername(),
                tUserAccount.getBirthStr(),
                tUserAccount.getNickname(),
                tUserAccount.getPhoto(),
                tUserAccount.getCerttype(),
                tUserAccount.getCerttypename(),
                tUserAccount.getAreacode(),
                tUserAccount.getOpenflag(),
                tUserAccount.getAddress(),
                tUserAccount.getParams(),
                tUserAccount.getMail());

        if(basePlpgsqlModel.getRetcode()>=0){
            log.info(String.format("%s修改成功！", tUserAccount.getAcc()));
        }else if(-2==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_EXIST);
        }else if(-6==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_IS_NO_NULL);
        }else if(-3==basePlpgsqlModel.getRetcode()){
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_PHONE_EXIST);
        }else{
            throw new BIZException(ResponseResult.DB_ERROR);
        }*/
//        //如果不给公卫账号就不插入
//        if(!tUserAccount.getGwusername().isEmpty() && tUserAccount.getGwusername() == "") {
//
//            String password = Base64Util.encryptBASE64(tUserAccount.getGwpassword().getBytes());
//            basePlpgsqlModel = fGwAccountUpdateRepository.fGwAccountUpdate(tUserAccount.getUserid(),
//                    tUserAccount.getHospcode(),
//                    tUserAccount.getGwusername(),
//                    password,
//                    tUserAccount.getGwdoctorname(),
//                    tUserAccount.getGwhospadminflag());
//            vaildateRetCode(basePlpgsqlModel.getRetcode());
//        }

    }

    /**
     * 重置密码
     *
     * @param tUserAccount
     */
    @Override
    public void resetUserAccountPwd(TUserAccount tUserAccount) {
        String salt = UUIDUtils.getUUID();
        String pwd = DigestUtils.md5Hex("123456" + salt);
        BasePlpgsqlModel basePlpgsqlModel = mbUserAccountRepository.resetUserAccountPwd(
                tUserAccount.getUserid(),
                tUserAccount.getAcc(),
                pwd,
                salt
        );
        if (-6 == basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_IS_NO_NULL);
        } else if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }
    }

    /**
     * 修改密码 在updateUserAccount的时候实现了
     *
     * @param acc
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void modUserAccountPwd(String userid, String acc, String oldPwd, String newPwd) {
        String salt = UUIDUtils.getUUID();
        String pwd = DigestUtils.md5Hex(newPwd + salt);
        BasePlpgsqlModel basePlpgsqlModel = mbUserAccountRepository.resetUserAccountPwd(
                userid,
                acc,
                pwd,
                salt
        );
        if (-6 == basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_IS_NO_NULL);
        } else if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.COMMON_ERROR_UNKNOWN);
        }
    }


    @Override
    public Page<Cmd44110Resp> queryUserAccountRole(RoleGroupFilter roleGroupFilter) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountRoleQueryRepository.fMbUserAccountRoleQuery(
                roleGroupFilter.getUserid(),
                roleGroupFilter.getRolegrpid(),
                roleGroupFilter.getRolegrpname()
        );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        Cmd44110Resp[] userAccountArray = basePlpgsqlModel2Clz(basePlpgsqlModel, Cmd44110Resp[].class);

        if (null != userAccountArray) {
            List<Cmd44110Resp> tUserAccounts = CollectionUtils.arrayToList(userAccountArray);
            Page<Cmd44110Resp> pageList = new PageImpl<Cmd44110Resp>(tUserAccounts, null, (long) basePlpgsqlModel.getRetcode());
            return pageList;
        }
        return null;
    }

    /*@Override
    public void updateUserAccountHospauthflag(MbUserAccountFilter filter) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountHospauthflagUpdateRepository.fMbUserAccountHospauthflagUpdate(
                filter.getUserid(),
                filter.getAcc(),
                filter.getHospauthflag()
        );
        vaildateRetCode(basePlpgsqlModel.getRetcode());
    }

    @Override
    public void updateUserAccountDeptauthflag(MbUserAccountFilter filter) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountDeptauthflagUpdateRepository.fMbUserAccountDeptauthflagUpdate(
                filter.getUserid(),
                filter.getAcc(),
                filter.getDeptauthflag()
        );
        vaildateRetCode(basePlpgsqlModel.getRetcode());
    }

    @Override
    public void updateUserAccountDoctorauthflag(MbUserAccountFilter filter) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountDoctorauthflagUpdateRepository.fMbUserAccountDoctorauthflagUpdate(
                filter.getUserid(),
                filter.getAcc(),
                filter.getDoctorauthflag()
        );
        vaildateRetCode(basePlpgsqlModel.getRetcode());
    }

    @Override
    public void updateUserAccountAreaauthflag(MbUserAccountFilter filter) {
        BasePlpgsqlModel basePlpgsqlModel = fMbUserAccountAreaauthflagUpdateRepository.fMbUserAccountAreaauthflagUpdate(
                filter.getUserid(),
                filter.getAcc(),
                filter.getAreaauthflag()
        );
        vaildateRetCode(basePlpgsqlModel.getRetcode());
    }*/
}
