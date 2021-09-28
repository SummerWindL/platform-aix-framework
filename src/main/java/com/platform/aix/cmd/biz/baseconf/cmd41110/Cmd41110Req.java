package com.platform.aix.cmd.biz.baseconf.cmd41110;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加项目
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41110Req extends BaseCommonRequest {

    @NotNull(message = "项目名称不能为空")
    private String projectname;
    private String projectdesc = "";
    private String hospcode = "";
}