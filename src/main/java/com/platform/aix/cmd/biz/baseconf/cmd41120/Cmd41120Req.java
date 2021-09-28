package com.platform.aix.cmd.biz.baseconf.cmd41120;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 删除项目
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41120Req extends BaseCommonRequest {

    @NotNull(message = "项目ID不能为空")
    private String projectid;
}