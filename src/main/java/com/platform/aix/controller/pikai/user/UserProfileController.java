package com.platform.aix.controller.pikai.user;

import com.platform.aix.common.datacommon.db.domain.PikaiUser;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserService;
import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.enums.ResponseCode;
import com.platform.aix.controller.pikai.common.exception.BusinessException;
import com.platform.aix.controller.pikai.common.request.PikaiPasswordReq;
import com.platform.aix.controller.pikai.common.request.PikaiUserReq;
import com.platform.common.util.BeanUtil;
import com.platform.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 个人信息接口
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月09日 17:46
 */
@RestController
@RequestMapping("/pikai/api/user")
public class UserProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PikaiUserService pikaiUserService;
    @GetMapping("profile/{userId}")
    public ApiResponse<PikaiUser> getPikaiUser(@PathVariable String userId){
        return ApiResponse.success(pikaiUserService.selectOne(userId));
    }

    @PostMapping("profile")
    public ApiResponse<String> getPikaiUser(@RequestBody PikaiUserReq pikaiUserReq){
        PikaiUser user = new PikaiUser();
        BeanUtil.copyPropertiesIgnoreNull(user,pikaiUserReq);
        pikaiUserService.doUpdate(user);
        return ApiResponse.success("成功");
    }
    @PostMapping("password")
    public ApiResponse<String> updatePassword(@RequestBody PikaiPasswordReq pikaiPasswordReq){
        if (!StringUtil.equals(pikaiPasswordReq.getNewPassword(),pikaiPasswordReq.getConfirmPassword())){
            throw new BusinessException(ResponseCode.PASSWORD_ARE_NOT_EQUALS.getCode(), "两次密码输入不匹配");
        }
        PikaiUser user = pikaiUserService.selectOne(pikaiPasswordReq.getUserId());
        boolean isPasswordValid = validatePassword(
                pikaiPasswordReq.getOldPassword(),
                user.getPassword(),
                user.getSalt(),
                user.getEncrypType()
        );

        if (!isPasswordValid) {
            throw new BusinessException(ResponseCode.PASSWORD_ARE_NOT_MATCH.getCode(), "原密码错误");
        }

        pikaiUserService.updatePassword(pikaiPasswordReq);
        return ApiResponse.success("成功");
    }

    private boolean validatePassword(String rawPassword, String storedPassword, String salt, String encrypType) {
        if ("BCRYPT".equals(encrypType)) {
            return passwordEncoder.matches(rawPassword + salt, storedPassword);
        }
        throw new UnsupportedOperationException("不支持的加密类型: " + encrypType);
    }
}
