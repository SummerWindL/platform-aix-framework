package com.platform.aix.cmd.bean.filter;

import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;


/**
 * @program: platform-aimb
 * @description: 过滤基类
 * @author: fuyl
 * @create: 2019-01-07 14:17
 **/

@Setter
@ToString
public class BaseFilter {

    /**
     * 所有请求接口需要传递userid，以便日后写日志文件用
     */
    private String userid;

    public String getUserid() {
        return StringUtils.isEmpty(userid) ? "":userid;
    }
}
