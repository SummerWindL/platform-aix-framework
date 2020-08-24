package com.platform.aix.cmd.bean.request;

import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;


/**
 * @program: platform-aimb
 * @description: 新定义接口Request请求，处理一些默认需要传递的参数，方便扩展
 * @author: fuyl
 * @create: 2019-01-07 14:08
 **/

@Setter
@ToString
public class BaseCommonRequest extends BaseRequest{

    private String factoryid = "";
    /**
     * 所有请求接口需要传递userid，以便日后写日志文件用
     */
    private String userid = "";

    private String ssid ="";

    public String getUserid() {
        return StringUtils.isEmpty(userid) ? "" : userid;
    }

    public String getFactoryid() {
        return StringUtils.isEmpty(factoryid) ? "" : factoryid;
    }
}
