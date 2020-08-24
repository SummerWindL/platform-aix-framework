package com.platform.aix.common.handler;


import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.APIResponse;

import java.io.IOException;


public interface CommandHandler {

    public abstract APIResponse handle(BaseRequest request);

    public abstract Class<? extends BaseRequest> getRequestClass();

    public abstract APIResponse execute(BaseRequest request) throws BIZException, IOException;
}
