package com.platform.aix.cmd.cmd10000;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Advance
 * @date 2021年09月24日 8:54
 * @since V1.0.0
 */
@Data
public class Cmd10000Req {

    @NotNull(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String password;
}
