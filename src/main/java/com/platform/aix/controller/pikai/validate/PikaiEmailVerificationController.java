package com.platform.aix.controller.pikai.validate;

import com.google.protobuf.Api;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiEmailVerificationService;
import com.platform.aix.controller.pikai.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月21日 14:58
 */
@Slf4j
@RestController
@RequestMapping("/pikai/api/email")
public class PikaiEmailVerificationController {
    @Autowired
    private PikaiEmailVerificationService pikaiEmailVerificationService;

    @PostMapping("/resend")
    public ApiResponse<?> resendVerificationEmail(@RequestParam String email) {
        boolean sent = pikaiEmailVerificationService.sendVerificationEmail(email);

        Map<String, Object> response = new HashMap<>();
        if (sent) {
            response.put("status", "success");
            response.put("message", "验证邮件已发送");
        } else {
            response.put("status", "error");
            response.put("message", "验证邮件发送失败");
        }

        return ApiResponse.success(response);
    }

    /**
     * 用户点击邮箱验证按钮 验证成功直接跳转到验证成功页面
     * http://localhost:8071/pikai/api/email/verify?code=a66deed94b044bd4abc47a1ead0f53f1
     * @author fuyanliang
     * @date 2025/4/22 11:48
     * @param code
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     */
    @GetMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam String code) {
        boolean verified = pikaiEmailVerificationService.verifyEmail(code);

        Map<String, Object> response = new HashMap<>();
        String frontendUrl;
        if (verified) {
            response.put("status", "success");
            response.put("message", "邮箱验证成功");
            // TODO 需要做成配置
            frontendUrl = "http://localhost:3000/verify-success?status=success";
        } else {
            response.put("status", "error");
            response.put("message", "邮箱验证失败");
            frontendUrl = "http://localhost:3000/verify-success?status=failed";
        }
        // 返回302重定向响应
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(frontendUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
