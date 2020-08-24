package com.platform.aix.cmd.cmd10010;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.aix.cmd.bean.request.BaseCommonRequest;
import lombok.Data;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 15:27
 **/
@Data
public class Cmd10010Req extends BaseCommonRequest {
    @JsonProperty("MachineId")
    private String machineId;         //机器编码
    @JsonProperty("MacAddr")
    private String macAddr;     //体检机物理网卡
    @JsonProperty("UnitNo")
    private String unitNo;//单位编号
    @JsonProperty("UnitName")
    private String unitName ;//单位名称
    @JsonProperty("DoctorId")
    private String doctorId;//医生工号
    @JsonProperty("DoctorName")
    private String doctorName;//医生姓名
    @JsonProperty("RecordNo")
    private String recordNo;//测量编号
    @JsonProperty("MeasureTime")
    private String measureTime;//测量时间
    @JsonProperty("LoginType")
    private String loginType;//登录方式
    @JsonProperty("DeviceType")
    private String deviceType;//设备型号
    @JsonProperty("Member")
    private JSONObject member;//用户信息
    @JsonProperty("Height")
    private JSONObject height;//身高体重
    @JsonProperty("Fat")
    private JSONObject fat;//人体成分（脂肪）
    @JsonProperty("MinFat")
    private JSONObject minFat;//人体成分（脂肪）
    @JsonProperty("BloodPressure")
    private JSONObject bloodPressure;//血压
    @JsonProperty("Bo")
    private JSONObject bo;//血氧
    @JsonProperty("Ecg")
    private JSONObject ecg;//单导心电
    @JsonProperty("PEEcg")
    private JSONObject PEEcg;//12导心电
    @JsonProperty("Temperature")
    private JSONObject temperature;//体温
    @JsonProperty("Whr")
    private JSONObject whr;//腰臀比
    @JsonProperty("BloodSugar")
    private JSONObject bloodSugar;//血糖
    @JsonProperty("Ua")
    private JSONObject ua;//血尿酸
    @JsonProperty("Chol")
    private JSONObject chol;//总胆固醇
    @JsonProperty("BloodFat")
    private JSONObject bloodFat;//血脂
    @JsonProperty("Cardiovascular")
    private JSONObject cardiovascular;//心血管
    @JsonProperty("BMD")
    private JSONObject BMD;//骨密度
    @JsonProperty("Hb")
    private JSONObject hb;//血红蛋白
    @JsonProperty("Alcohol")
    private JSONObject alcohol;//酒精浓度
    @JsonProperty("Lung")
    private JSONObject lung;//肺活量
    @JsonProperty("Urinalysis")
    private JSONObject urinalysis;//尿液分析
    @JsonProperty("ArteryAve")
    private JSONObject arteryAve;//兆和动脉硬化
}
