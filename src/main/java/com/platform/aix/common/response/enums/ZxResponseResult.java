package com.platform.aix.common.response.enums;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 17:59
 **/

public enum ZxResponseResult {

    // +++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++基础级别 错误+++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++
    COMMON_ERROR("false", "基础错误"),
    COMMON_ERROR_EXCEPTION("false", "异常错误"),
    COMMON_ERROR_UNKNOWN("false", "未知错误"),
    COMMON_ERROR_INVALID_PARAM("false", "无效参数"),
    COMMON_ERROR_NULL_POINTER("false", "空指针"),
    COMMON_ERROR_ALLOC_FAILED("false", "分配内存错误"),
    COMMON_ERROR_CONVERT_PARAM("false", "参数类型转换错误"),
    COMMON_ERROR_PARAM_NOT_NULL("false", "参数不能为空"),

    // ++++++++++++++++++HTTP 通讯协议错误+++++++++++++++++++++++
    HTTP_ERROR("false", "HTTP错误"),
    HTTP_ACCESS_FREQUENCY("false", "请求太频繁"),
    HTTP_ERROR_INVALID_REQUEST("false", "请求命令无效"),
    HTTP_ERROR_INVALID_PARAM("false", "参数不正确"),
    HTTP_ERROR_INVALID_SIGN("false", "签名无效"),
    HTTP_ERROR_POST_FAILURE("false", "POST请求失败"),
    HTTP_ERROR_PARSE_JSON("false", "JSON格式解析失败"),
    HTTP_ERROR_REQUEST_INTERCEPTOR("false", "请求被拦截"),
    HTTP_ERROR_UNSURPORT_PROTO_VER("false", "不支持的协议版本"),
    HTTP_ERROR_URL_ENCODE_ERROR("false", "URL编码错误"),
    HTTP_ERROR_WRITE_JSON("false", "生成JSON失败"),
    HTTP_ERROR_INVALID_AUTH("false", "非法的权限"),

    //++++++++++++++++++互联网业务错误++++++++++++++++++++++++
    BIZ_ERROR_SS_NOT_EXIST("false", "居民档案信息不存在"),
    // ++++++++++++++++++成功+++++++++++++++++++++++
    ZX_SUCCESS("true", "成功"),
    COMMON_SUCCESS("true", "成功");


    private String success;

    private String message;

    private ZxResponseResult(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
