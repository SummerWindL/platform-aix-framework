package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiUserMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.common.exception.AuthenticationException;
import com.platform.aix.controller.pikai.common.LoginRequest;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.controller.pikai.common.request.PikaiPasswordReq;
import com.platform.aix.controller.pikai.common.request.PikaiUserReq;
import com.platform.common.util.BeanUtil;
import com.platform.common.util.DateUtil;
import com.platform.common.util.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 16:10
 */
@Service
@RequiredArgsConstructor
public class PikaiUserServiceImpl extends AsyncServiceImpl<String, PikaiUser> implements PikaiUserService {
    @Autowired
    private PikaiUserMapper pikaiUserMapper;
    @Override
    public IMybatisMapper<PikaiUser, String, ?> getMapper() {
        return pikaiUserMapper;
    }
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public PikaiUser authenticate(String email, String password) {
        // 1. 根据email查找用户
        PikaiUser user = pikaiUserMapper.findByAccountId(email)
                .orElseThrow(() -> new AuthenticationException("用户不存在"));

        // 2. 检查账户状态
        if (!"0".equals(user.getAccountStatus())) {
            throw new AuthenticationException("账户状态异常");
        }

        // 3. 验证密码
        String encryptedInputPassword = encryptPassword(password, user.getSalt(), user.getEncrypType());
        if (!encryptedInputPassword.equals(user.getPassword())) {
            throw new AuthenticationException("密码错误");
        }

        return user;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PikaiUser pikaiUser) {

    }

    @Override
    protected void save(PikaiUser pikaiUser) {

    }

    @Override
    protected void delete(String PK) {

    }

    @Override
    public PikaiUser doSave(LoginRequest registerRequest) {
        // 2. 生成随机盐值
        String salt = UUIDUtils.getUUID();

        // 3. 加密密码
        String encryptedPassword = encryptPassword(
                registerRequest.getPassword(),
                salt,
                "BCRYPT" // 使用BCRYPT加密方式
        );

        // 4. 创建用户对象
        PikaiUser user = new PikaiUser();
        user.setUserId(UUIDUtils.getUUID().toString());
        user.setAccountId(registerRequest.getEmail());
        user.setUserName(registerRequest.getEmail().split("@")[0]); // 默认用户名
        user.setSalt(salt);
        user.setPassword(encryptedPassword);
        user.setEncrypType("BCRYPT");
        user.setAccountStatus("0"); // 0表示正常状态
        user.setCreateTime(DateUtil.now());
        user.setUpdateTime(DateUtil.now());
        return pikaiUserMapper.insertReturnEntity(user);
    }

    @Override
    public void doUpdate(PikaiUser user) {
        pikaiUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PikaiUser findByAccountId(String email) {
        return pikaiUserMapper.findByAccountId(email).orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND.getCode(),"用户不存在"));
    }

    @Override
    public boolean existsByAccountId(String email) {
        return pikaiUserMapper.existsByAccountId(email);
    }

    @Override
    public PikaiUser selectOne(String userId) {
        return pikaiUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updatePassword(PikaiPasswordReq pikaiPasswordReq) {
        PikaiUser pikaiUser = new PikaiUser();
        // 2. 生成随机盐值
        String salt = UUIDUtils.getUUID();

        // 3. 加密密码
        String encryptedPassword = encryptPassword(
                pikaiPasswordReq.getNewPassword(),
                salt,
                "BCRYPT" // 使用BCRYPT加密方式
        );
        pikaiUser.setUserId(pikaiPasswordReq.getUserId());
        pikaiUser.setSalt(salt);
        pikaiUser.setPassword(encryptedPassword); // 加密后新密码
        pikaiUserMapper.updateByPrimaryKeySelective(pikaiUser);
    }

    @Override
    public void updateUserInfo(PikaiUserReq pikaiUserReq) {
        PikaiUser pikaiUser = new PikaiUser();
        BeanUtil.copyPropertiesIgnoreNull(pikaiUser,pikaiUserReq);
        pikaiUserMapper.updateOrInsertSelective(pikaiUser);
    }

    private String encryptPassword(String rawPassword, String salt, String encrypType) {
        if ("BCRYPT".equals(encrypType)) {
            return passwordEncoder.encode(rawPassword + salt);
        }
        throw new UnsupportedOperationException("不支持的加密类型: " + encrypType);
    }

}
