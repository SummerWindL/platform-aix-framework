package com.platform.aix.common.handler.base;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.ZxApiResponse;
import com.platform.aix.common.response.enums.ZxResponseResult;
import com.platform.aix.config.ApisPorperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description: 扩展-双佳返回结构体
 * @author: fuyl
 * @create: 2020-08-24 10:48
 **/

public class CommandSonkaExtHandler extends CommandAbstractBaseHandler{
    private static Map<Class<?>, List<Field>> CLZ_FIELDS = new HashMap<Class<?>, List<Field>>();

    private final Log log = LogFactory.getLog(CommandSonkaExtHandler.class);

    @Autowired
    private ApisPorperties apisPorperties;

    public ZxApiResponse checkParamsValid(BaseRequest request) {
        return new ZxApiResponse(ZxResponseResult.COMMON_SUCCESS);
    }
    @Override
    public <T> T handle(BaseRequest request) {

        // HTTP请求响应
        ZxApiResponse response = null;

        // 1.对request对象的注解进行校验 TODO
        try {
            IUtil.validateParams(CLZ_FIELDS,request);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            // 2.若校验接口抛出异常，则表示注解验证未通过
            response = new ZxApiResponse(ZxResponseResult.HTTP_ERROR_INVALID_PARAM);
            response.setMessage(e.getMessage());
            return (T) response;
        }

        // 3.验证请求参数字段合法性
        response = checkParamsValid(request);
        if (!response.getSuccess().equals(ZxResponseResult.COMMON_SUCCESS.getSuccess())) {
            return (T) response;
        }


        // 4.执行业务逻辑，业务处理流程错误通过抛异常的方式进行处理
        try {
            // 请求参数字段转换
            IUtil.translateParams(CLZ_FIELDS,request);
            response = execute(request);
        } catch (BIZException e) {
            log.info(e.getMessage(), e);
            response = new ZxApiResponse(e.getResponseResult());
            response.setData(e.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new ZxApiResponse(ZxResponseResult.COMMON_ERROR_EXCEPTION);
        }

        return (T) response;

    }
}
