package com.platform.aix.cmd.biz.cmd30030;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author: Advance
 * @date: 2018/4/18
 * @description:
 */
@Setter
@Getter
@ToString
public class Cmd30030Req extends BaseCommonRequest {

    /**
     * 校验方法
     * @author Advance
     * @date 2022/3/19 11:45
     */
    public void check(){
        System.out.println("调用校验方法！");
    }

}
