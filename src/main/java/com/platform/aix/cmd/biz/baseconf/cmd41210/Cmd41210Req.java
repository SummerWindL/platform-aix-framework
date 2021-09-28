package com.platform.aix.cmd.biz.baseconf.cmd41210;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加项目标题
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41210Req extends BaseCommonRequest {

    @NotNull(message = "项目ID不能为空")
    private String projectid;
    @NotNull(message = "项目标题名称不能为空")
    private String titlename;
    @NotNull(message = "项目标题Logo不能为空")
    private String titlelogo;
}