package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiUserMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.common.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String encryptPassword(String rawPassword, String salt, String encrypType) {
        // 根据加密类型选择不同的加密方式
        switch (encrypType) {
            case "SHA256":
                return DigestUtils.sha256Hex(rawPassword + salt);
            case "BCRYPT":
                return passwordEncoder.encode(rawPassword);
            default:
                throw new UnsupportedOperationException("不支持的加密类型: " + encrypType);
        }
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
    public PikaiUser doSave(PikaiUser user) {
        return pikaiUserMapper.insertReturnEntity(user);
    }

    @Override
    public void doUpdate(PikaiUser user) {
        pikaiUserMapper.updateByPrimaryKey(user);
    }

    @Override
    public PikaiUser findByAccountId(String email) {
        return pikaiUserMapper.findByAccountId(email).orElseThrow(() -> new AuthenticationException("用户不存在"));
    }

    @Override
    public boolean existsByAccountId(String email) {
        return pikaiUserMapper.existsByAccountId(email);
    }
}
