package com.platform.aix.service.user.inspection.bean;

import lombok.Data;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 19:16
 **/
@Data
public class InspectionCommonBean {
    private String machineId;  //机器编码
    private String macAddr; //体检机物理网卡
    private String unitNo;//单位编号
    private String unitName ;//单位名称
    private String doctorId;//医生工号
    private String doctorName;//医生姓名
    private String recordNo;//测量编号
    private String measureTime;//测量时间
    private String loginType;//登录方式
    private String deviceType;//设备型号
}
