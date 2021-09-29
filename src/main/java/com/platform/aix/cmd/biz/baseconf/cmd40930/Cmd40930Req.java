package com.platform.aix.cmd.biz.baseconf.cmd40930;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd40930Req extends BaseCommonRequest {

    @NotNull(message = "角色组ID不能为空")
    private String rolegrpid;
}