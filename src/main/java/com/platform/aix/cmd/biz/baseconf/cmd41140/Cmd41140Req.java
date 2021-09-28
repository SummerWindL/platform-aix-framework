package com.platform.aix.cmd.biz.baseconf.cmd41140;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改项目
 *
 * @author: fyw
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd41140Req extends BaseCommonRequest {

    @NotNull(message = "项目ID不能为空")
    private String projectid;
    @NotNull(message = "项目名称不能为空")
    private String projectname;
    private String projectdesc = "";
    private String hospcode = "";
}