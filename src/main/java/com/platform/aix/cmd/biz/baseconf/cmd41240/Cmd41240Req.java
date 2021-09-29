package com.platform.aix.cmd.biz.baseconf.cmd41240;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改项目标题
 *
 * @author: Advance
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41240Req extends BaseCommonRequest {

    @NotNull(message = "项目ID不能为空")
    private String projectid;
    @NotNull(message = "项目标题ID不能为空")
    private String titleid;
    @NotNull(message = "项目标题名称不能为空")
    private String titlename;
    @NotNull(message = "项目标题Logo不能为空")
    private String titlelogo;
}