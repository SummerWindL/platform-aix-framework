package com.platform.aix.cmd.biz.baseconf.cmd40920;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd40920Req extends BaseCommonRequest {

    @NotNull(message = "角色组ID不能为空")
    private String rolegrpid;

    @NotNull(message = "功能ID不能为空")
    private String functionidlistjosn;
}