package com.platform.aix.cmd.biz.baseconf.cmd40640;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改角色
 *
 * @author: fyw
 * @date: 2018/7/28
 * @description:
 */
@Data
public class Cmd40640Req extends BaseCommonRequest {

    @NotNull(message = "角色组ID不能为空")
    private String rolegrpid;
    @NotNull(message = "角色名不能为空")
    private String rolegrpname;
    private String rolegrpicon;
}
