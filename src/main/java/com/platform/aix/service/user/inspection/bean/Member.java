/**
  * Copyright 2020 bejson.com 
  */
package com.platform.aix.service.user.inspection.bean;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2020-08-20 20:31:5
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Member {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Mobile")
    private String mobile;
    @JsonProperty("IdCode")
    private String idCode;
    @JsonProperty("Age")
    private String age;
    @JsonProperty("Sex")
    private String sex;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Birthday")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    @JsonProperty("UserIcon")
    private String userIcon;
    @JsonProperty("Nation")
    private String nation;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("BarCode")
    private String barCode;
    @JsonProperty("IcCode")
    private String icCode;
    @JsonProperty("SocialCode")
    private String socialCode;
    @JsonProperty("UserID")
    private String userID;

    private String ssid;


}