package com.platform.aix.cmd.biz.Cmd30040;

import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import com.platform.aix.common.annotation.Decryption;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author: Advance
 * @date: 2018/4/18
 * @description:
 */
@Setter
@Getter
@ToString
public class Cmd30040Req extends BaseCommonRequest {


    @NotNull(message = "用户名或密码不能为空！")
    private String acc;

    @NotNull(message = "用户名或密码不能为空！")
    @Decryption
    private String oldpwd;

    @NotNull(message = "用户名或密码不能为空！")
    @Decryption
    private String newpwd;
}
