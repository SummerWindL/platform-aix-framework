package com.platform.aix.cmd.biz.baseconf.cmd40910;

import com.platform.aix.cmd.bean.request.BaseRequestPage;
import lombok.Data;

/**
 * 查询角色组功能关联
 *
 * @author: Advance
 * @date: 2018/7/30
 * @description:
 */
@Data
public class Cmd40910Req extends BaseRequestPage {

    private String rolegrpid;
}