package com.platform.aix.cmd.biz.baseconf.cmd40620;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除角色
 *
 * @author: fyw
 * @date: 2018/7/28
 * @description:
 */
@Data
public class Cmd40620Req extends BaseCommonRequest {
    @NotNull(message = "角色组ID不能为空")
    private String rolegrpid;
}
