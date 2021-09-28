package com.platform.aix.service.user.account.bean;

import com.platform.aix.cmd.bean.filter.BasePageFilter;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class UserAccountFilter extends BasePageFilter {

    private String acc = "";

    private String userid = "";

    private String hospcode = "";

    private String doctoruserid;

    private String deptcode;

    private String hospname;

    private String doctorname;

    private String deptname;

    public String getDoctoruserid() {
        return StringUtils.isEmpty(doctoruserid) ? "" : doctoruserid ;
    }

    public String getDeptcode() {
        return StringUtils.isEmpty(deptcode) ? "" : deptcode ;
    }

    public String getHospname() {
        return StringUtils.isEmpty(hospname) ? "" : hospname ;
    }

    public String getDoctorname() {
        return StringUtils.isEmpty(doctorname) ? "" : doctorname ;
    }

    public String getDeptname() {
        return StringUtils.isEmpty(deptname) ? "" : deptname ;
    }

    public String getAcc() {
        return StringUtils.isEmpty(acc) ? "" : acc;
    }

    @Override
    public String getUserid() {
        return StringUtils.isEmpty(userid) ? "" : userid;
    }

    public String getHospcode() {
        return StringUtils.isEmpty(hospcode) ? "" : hospcode;
    }

}