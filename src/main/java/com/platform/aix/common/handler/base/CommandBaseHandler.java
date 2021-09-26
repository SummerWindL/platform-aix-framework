package com.platform.aix.common.handler.base;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: AIX统一返回结构体
 * @author: fuyl
 * @create: 2020-08-24 10:47
 **/

public class CommandBaseHandler extends CommandAbstractBaseHandler{

    private static Map<Class<?>, List<Field>> CLZ_FIELDS = new HashMap<Class<?>, List<Field>>();

    private final Log log = LogFactory.getLog(CommandBaseHandler.class);


    public APIResponse checkParamsValid(BaseRequest request) {
        return new APIResponse(ResponseResult.COMMON_SUCCESS);
    }

    @Override
    public <T> T handle(BaseRequest request) {
        // HTTP请求响应
        APIResponse response = null;

        // 1.对request对象的注解进行校验 TODO
        try {
            IUtil.validateParams(CLZ_FIELDS,request);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            // 2.若校验接口抛出异常，则表示注解验证未通过
            response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_PARAM);
            response.setResultmsg(e.getMessage());
            return (T) response;
        }

        // 3.验证请求参数字段合法性
        response = checkParamsValid(request);
        if (!response.equals(ResponseResult.COMMON_SUCCESS)) {
            return (T) response;
        }


        // 4.执行业务逻辑，业务处理流程错误通过抛异常的方式进行处理
        try {
            // 请求参数字段转换
            IUtil.translateParams(CLZ_FIELDS,request);
            response = execute(request);
        } catch (BIZException e) {
            log.info(e.getMessage(), e);
            response = new APIResponse(e.getResponseResult());
            response.setData(e.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new APIResponse(ResponseResult.COMMON_ERROR_EXCEPTION);
        }

        return (T) response;
    }


}
