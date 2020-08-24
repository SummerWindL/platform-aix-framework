package com.platform.aix.common.response;

import com.platform.aix.common.response.enums.ZxResponseResult;
import lombok.Data;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 19:02
 **/
@Data
public class ZxApiResponse {
    protected String success = ZxResponseResult.COMMON_SUCCESS.getSuccess();

    protected String message = ZxResponseResult.COMMON_SUCCESS.getMessage();

    protected Object data;

    public ZxApiResponse() {
    }

    public ZxApiResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public ZxApiResponse(String success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ZxApiResponse(Object data) {
        this.data = data;
    }


    public ZxApiResponse(ZxResponseResult resp) {
        this.success = resp.getSuccess();
        this.message = resp.getMessage();
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
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
