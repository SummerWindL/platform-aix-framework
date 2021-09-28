package com.platform.aix.cmd.biz.cmd30010;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.cmd.bean.request.LoginInfo;
import com.platform.aix.common.annotation.Decryption;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author: Alfred
 * @date: 2018/4/18
 * @description:
 */
@Setter
@Getter
@ToString
public class Cmd30010Req extends BaseRequest {

    @NotNull(message = "帐号不能为空")
    private String acc;

    @NotNull(message = "密码不能为空")
    @Decryption
    private String pwd;

    // 登录选择公卫划区
    @NotNull(message = "县域区划不能为空")
    private String gwregioncode;

    @Valid
    private LoginInfo logininfo;

}
