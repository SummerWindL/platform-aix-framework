package com.platform.aix.cmd.biz.baseconf.cmd41220;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除项目标题
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41220Req extends BaseCommonRequest {

    @NotNull(message = "项目ID不能为空")
    private String projectid;
    @NotNull(message = "项目标题ID不能为空")
    private String titleid;
}