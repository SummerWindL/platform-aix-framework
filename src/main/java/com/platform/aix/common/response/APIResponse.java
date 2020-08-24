package com.platform.aix.common.response;

import com.platform.aix.common.response.enums.ResponseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 平台响应结果、参数校验公用
 *
 * @author Michael
 */
@Setter
@Getter
@ToString
public class APIResponse {

    protected int resultcode = ResponseResult.COMMON_SUCCESS.getResultcode();

    protected String resultmsg = ResponseResult.COMMON_SUCCESS.getResultmsg();

    protected Object data;

    public APIResponse() {
    }


    public APIResponse(int resultcode, String resultmsg) {
        this.resultcode = resultcode;
        this.resultmsg = resultmsg;
    }

    public APIResponse(int resultcode, String resultmsg, Object data) {
        this.resultcode = resultcode;
        this.resultmsg = resultmsg;
        this.data = data;
    }

    public APIResponse(Object data) {
        this.data = data;
    }

    public APIResponse(ResponseResult resp) {
        this.resultcode = resp.getResultcode();
        this.resultmsg = resp.getResultmsg();
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(ResponseResult r) {
        return (this.resultcode == r.getResultcode()) ? true : false;
    }
}
