package com.platform.aix.common.handler.base;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.exception.BIZException;

import java.io.IOException;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 11:37
 **/

public abstract class CommandAbstractBaseHandler implements IBaseHandler {
    @Override
    public <T> T handle(BaseRequest request) {
        return null;
    }

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return null;
    }

    @Override
    public <T> T execute(BaseRequest request) throws BIZException, IOException {
        return null;
    }
}
