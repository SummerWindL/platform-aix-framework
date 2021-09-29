package com.platform.aix.common.validator;

import com.platform.common.validator.Validator;
import org.springframework.util.StringUtils;

/**
 * 账号注解 @Account 校验器
 *
 * @ClassName        : AccountValidator
 * @author            : Advance
 * @date : 2017年5月12日 上午10:52:31
 */
public class AccountValidator {

    /**
     * 账号校验方法，判断为手机或者邮箱 校验通过
     *
     * @param
     * @return boolean
     */
    public static boolean isValid(String value) {
        boolean isValid = false;
        if (StringUtils.hasText(value)) {
            if (value.indexOf("@") >= 0) {//Email
                isValid = EmailValidator.isValid(value);
            } else {
                isValid = Validator.isMobile(value);
            }
        }
        return isValid;
    }

}
