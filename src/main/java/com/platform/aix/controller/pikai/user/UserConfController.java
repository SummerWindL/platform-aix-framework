package com.platform.aix.controller.pikai.user;

import com.platform.aix.common.datacommon.db.domain.PikaiUserConf;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserConfService;
import com.platform.aix.controller.pikai.common.ApiResponse;
import com.platform.aix.controller.pikai.common.LoginRequest;
import com.platform.aix.controller.pikai.common.request.PikaiUserConfReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuyanliang
 * 用户配置 相关接口
 * @version V1.0.0
 * @date 2025年05月08日 17:00
 */
@RestController
@RequestMapping("/pikai/api/user")
public class UserConfController {

    @Autowired
    private PikaiUserConfService pikaiUserConfService;
    /**
     * 保存配置
     * @author fuyanliang
     * @date 2025/5/8 17:02
     * @param pikaiUserConfReq
     * @return com.platform.aix.controller.pikai.common.ApiResponse<java.lang.String>
     */
    @PostMapping("config")
    public ApiResponse<String> registerUser(@RequestBody PikaiUserConfReq pikaiUserConfReq) {
        pikaiUserConfService.doSave(pikaiUserConfReq);
        return ApiResponse.success("保存成功");
    }

    @GetMapping("config/{userId}/{confItemCode}")
    public ApiResponse<PikaiUserConf> registerUser(@PathVariable String userId, @PathVariable String confItemCode) {
        pikaiUserConfService.getPikaiUserConf(userId,confItemCode);
        return ApiResponse.success(pikaiUserConfService.getPikaiUserConf(userId,confItemCode));
    }
}
