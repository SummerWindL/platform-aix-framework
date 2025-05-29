package com.platform.aix.controller.pikai.user;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    /**
     * 头像上传
     * @author fuyanliang
     * @date 2025/5/29 14:29
     * @param file
     * @param userId
     * @return ResponseEntity<Map<String,Object>>
     */
    @PostMapping("/avatar")
    public ApiResponse<Map<String, Object>> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            @RequestParam("userId") String userId) {

        try {
            // 1. 验证文件类型和大小
            if (file.isEmpty()) {
                return ApiResponse.error(400,"请选择有效的头像文件");
            }

            // 2. 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // 3. 保存文件到指定目录
            byte[] fileBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
            // 4. 更新用户头像URL到数据库 (存储Base64字符串)
            String avatarUrl = "data:image/" + fileExtension.substring(1) + ";base64," + base64Image;
            HashMap<String, String> avatarJson = MapUtil.of("avatarUrl", avatarUrl);
            PikaiUserReq pikaiUserReq = new PikaiUserReq();
            pikaiUserReq.setUserId(userId);
            pikaiUserReq.setUserInfo(avatarJson);
            pikaiUserService.updateUserInfo(pikaiUserReq);
            // 5. 返回成功响应
            return ApiResponse.success(MapUtil.of("avatarUrl", avatarUrl));

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.INTERNAL_ERROR.getCode(), "服务器内部错误");
        }
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
