package com.platform.aix.common.request;

import com.platform.aix.cmd.bean.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * User 相关请求公共属性
 * 此类校验request参数中的userid信息（检验不允许为空）,若不需要校验该信息,request直接继承BaseRequest
 * <功能详细描述>
 *
 * @author Michael
 * @version 2.0
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Setter
@Getter
public class CommonUserParamsRequest extends BaseRequest {

    @NotNull(message = "userid不能为空")
    private String userid;

}
