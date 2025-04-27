//package com.platform.aix.controller.pikai.login;
//
//import com.platform.aix.common.datacommon.db.domain.PikaiUser;
//import com.platform.aix.controller.pikai.common.LoginRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
///**
// * 登录逻辑
// * @author fuyanliang
// * @version V1.0.0
// * @date 2025年04月17日 11:14
// */
//@RestController
//@RequestMapping("/pikai/api/login")
//public class PikaiLoginController {
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(
//            @RequestBody LoginRequest loginRequest,
//            HttpServletResponse response) {
//
//        // 1. 验证用户凭证
//        PikaiUser user = .authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//
//        // 2. 生成JWT token
//        String token = jwtTokenUtil.generateToken(user);
//
//        // 3. 设置cookie
//        Cookie cookie = new Cookie("token", token);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        // 4. 返回响应
//        Map<String, String> responseBody = new HashMap<>();
//        responseBody.put("token", token);
//        responseBody.put("userId", user.getId().toString());
//
//        return ResponseEntity.ok(responseBody);
//    }
//}
