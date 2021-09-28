package com.platform.aix.service.user.account.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.util.DateUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TUserAccount {

    //公卫账号相关字段 start
    private String gwusername;
    private String gwpassword;
    private String gwdoctorname;
    private Integer gwhospadminflag = 0;
    private Integer existgwaccount;
    //公卫账号相关字段 end

    private String userid;

    private String acc;

    private String pwd;

    private String salt;

    private Integer sysflag;    //系统标记：10000-管理员，20000-用户

    private Integer enableflag;

    private Date regtime;

    private String hospcode;

    private String hospname;

    private Integer hospinternetflag;   //互联网医院开通标记：0-未开通，1-开通

    private String deptcode;

    private String deptname;

    private String doctorlevelcode;

    private String doctorlevelname;

    private String workdepartment;

    private String idcardno;

    private String phone;

    private String workno;

    private String signature;

    private String resume;

    private String doctorname;

    private String nickname;

    private String gendercode;


    private String gendername;
    private String certtype;
    private String certtypename;
    private Integer openflag;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    private String photo;

    private String areacode;

    private String address;

    private String params;

    private String mail;

    private String appdoctoraccount;

    private String appdoctorname;

    private String appdoctorid;

    private List<AuthHosp> authospcode;

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public String getAcc() {
        return acc == null ? "" : acc;
    }

    public String getPwd() {
        return pwd == null ? "" : pwd;
    }

    public String getSalt() {
        return salt == null ? "" : salt;
    }

    public String getHospcode() {
        return hospcode == null ? "" : hospcode;
    }

    public String getHospname() {
        return hospname == null ? "" : hospname;
    }

    public String getDeptcode() {
        return deptcode == null ? "" : deptcode;
    }

    public String getDoctorlevelcode() {
        return doctorlevelcode == null ? "" : doctorlevelcode;
    }

    public String getWorkdepartment() {
        return workdepartment == null ? "" : workdepartment;
    }

    public String getIdcardno() {
        return idcardno == null ? "" : idcardno;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public String getWorkno() {
        return workno == null ? "" : workno;
    }

    public String getSignature() {
        return signature == null ? "" : signature;
    }

    public String getResume() {
        return resume == null ? "" : resume;
    }

    public String getName() {
        return doctorname == null ? "" : doctorname;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public String getGenderStr() {
        return gendername == null ? "" : gendername;
    }

    public String getBirthStr() {
        return birth == null ? "" : DateUtil.formatDate(birth);
    }

    public String getPhoto() {
        return photo == null ? "" : photo;
    }

    public String getAreacode() {
        return areacode == null ? "" : areacode;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public String getParams() {
        return params == null ? "" : params;
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

    public String getDoctorlevelname() {
        return doctorlevelname == null ? "" : doctorlevelname;
    }

    public String getCerttype() {
        return certtype == null ? "" : certtype;
    }

    public String getCerttypename() {
        return certtypename == null ? "" : certtypename;
    }

    public Integer getOpenflag() {
        return openflag == null? 0 : openflag;
    }

    public List<AuthHosp> getAuthospcode() {
        if(CollectionUtils.isEmpty(authospcode)){
            return new ArrayList<AuthHosp>();
        }
        return authospcode;
    }

    public String getGwusername() {
        return StringUtils.isEmpty(gwusername)?"":gwusername ;
    }

    public String getGwpassword() {
        return StringUtils.isEmpty(gwpassword)?"123456":gwpassword ;
    }

    public String getGwdoctorname() {
        return StringUtils.isEmpty(gwdoctorname)?"":gwdoctorname ;
    }

    public Integer getGwhospadminflag() {
        return gwhospadminflag;
    }

    public String getMail() {
        return StringUtils.isEmpty(mail)?"":mail ;
    }

    public Integer getSysflag() {
        return sysflag == null? -1 : sysflag;
    }


}

