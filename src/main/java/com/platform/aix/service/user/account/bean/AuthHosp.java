package com.platform.aix.service.user.account.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @program: platform-aimb
 * @description: 权限医院
 * @author: fuyl
 * @create: 2019-03-14 15:31
 **/
@Data
public class AuthHosp {

    private String hospcode;

    private String hospname;

    private String appdoctoraccount;

    private String appdoctorname;

    private String appdoctorid;

    public String getHospcode() {
        return StringUtils.isEmpty(hospcode)?"":hospcode;
    }

    public String getHospname() {
        return StringUtils.isEmpty(hospname)?"":hospname ;
    }

    public String getAppdoctoraccount() {
        return StringUtils.isEmpty(appdoctoraccount)?"":appdoctoraccount ;
    }

    public String getAppdoctorname() {
        return StringUtils.isEmpty(appdoctorname)?"":appdoctorname ;
    }

    public String getAppdoctorid() {
        return StringUtils.isEmpty(appdoctorid)?"":appdoctorid ;
    }
}
