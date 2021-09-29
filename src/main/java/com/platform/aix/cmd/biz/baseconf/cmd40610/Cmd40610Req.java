package com.platform.aix.cmd.biz.baseconf.cmd40610;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加角色
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Data
public class Cmd40610Req extends BaseCommonRequest {

    private String rolegrpid;
    @NotNull(message = "角色名不能为空")
    private String rolegrpname;
    private String rolegrpicon;
}
