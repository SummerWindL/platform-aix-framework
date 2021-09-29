package com.platform.aix.cmd.biz.baseconf.cmd41250;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

/**
 * @author: Advance
 * @date: 2018/8/5
 * @description:
 */
@Data
public class Cmd41250Req extends BaseCommonRequest {

    //医院所属项目标题
    private String hospcode;
}
