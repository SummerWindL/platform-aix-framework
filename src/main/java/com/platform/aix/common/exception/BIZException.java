/**
 * 文件名:BIZException.java
 * 版权:ikinloop
 * 描述: 业务层异常基类
 * 修改人:rivers
 * 修改时间:2017年3月31日
 * 修改内容:
 * 跟踪单号
 * 修改单号
 */

package com.platform.aix.common.exception;

import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.response.enums.ZxResponseResult;

/**
 *<业务异常>
 *<Service 处理业务逻辑判断返回错误码时抛出该异常类，而不是采用返回错误码的方式>
 *@author rivers
 *@version 2.0
 *@see [相关类/方法]
 *@since [产品/模块版本]
 */
public class BIZException extends RuntimeException {

    private static final long serialVersionUID = 970152056841039187L;

    private ResponseResult responseResult;

    private Object data;

    private ZxResponseResult zxResponseResult;

    public BIZException(ResponseResult responseResult) {
        this(responseResult, null);
    }

    public BIZException(ZxResponseResult responseResult) {
        this(responseResult, null);
    }

    public BIZException(ZxResponseResult responseResult, Object data) {
        this.zxResponseResult = responseResult;
        this.data = data;
    }

    public BIZException(ResponseResult responseResult, Object data) {
        this.responseResult = responseResult;
        this.data = data;
    }

    public ZxResponseResult getZxResponseResult() {
        return this.zxResponseResult;
    }

    public ResponseResult getResponseResult() {
        return this.responseResult;
    }

    public Object getData() {
        return this.data;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        if (zxResponseResult != null) {
            return zxResponseResult.getMessage();
        }

        return super.getMessage();
    }
}
