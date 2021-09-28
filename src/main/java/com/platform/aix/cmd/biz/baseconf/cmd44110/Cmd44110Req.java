package com.platform.aix.cmd.biz.baseconf.cmd44110;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

/**
 * @Author Yuxc
 * @create 2019/3/28
 */

@Data
public class Cmd44110Req extends BaseCommonRequest {

    private String userid;

    private String rolegrpid = "";
    private String rolegrpname = "";
}
