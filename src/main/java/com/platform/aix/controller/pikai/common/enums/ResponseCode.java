package com.platform.aix.controller.pikai.common.enums;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 19:18
 */
public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    // 业务相关扩展状态码
    EMAIL_EXISTS(10001, "邮箱已存在"),
    EMAIL_VERIFICATIONCODE_NOTEXISTS(10002,"邮箱验证码不存在"),
    EMAIL_VERIFICATION_NOT_PASS(10003,"未通过邮箱校验"),
    USER_NOT_FOUND(40000, "用户不存在"),
    USER_EXISTS(40001, "用户已存在"),
    ACCOUNT_DISABLED(50001, "账户已被禁用"),
    INVALID_CREDENTIALS(50001, "密码错误"),
    VERIFICATION_CODE_ERROR(60001,"验证码错误或已过期");




    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
