package com.platform.aix.common.handler.base;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.exception.BIZException;

import java.io.IOException;

/**
 * @program: platform-zxapi-gateway
 * @description: 最基础顶级接口类 兼容各种返回格式结构
 * @author: fuyl
 * @create: 2020-08-24 10:43
 **/
public interface IBaseHandler {
    public abstract <T> T handle(BaseRequest request);

    public abstract Class<? extends BaseRequest> getRequestClass();

    public abstract <T> T  execute(BaseRequest request) throws BIZException, IOException;
}
