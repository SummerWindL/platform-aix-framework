package com.platform.aix.service.user.inspection.bean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 11:28
 **/
@Data
public class DictInspectionBean {
    private String inspectionitemcode;
    private String inspectionitemname;
    private String inspectionsubitemcode;
    private String inspectionsubitemname;
    private String inspectionindexcode;
    private String inspectionindexname;
    private int inspectionindextype;
    private String inspectionindexunit;
    private String inspectionindexref;
    private String inspectionindexmemo;
    private int inspectionindexpointcnt;
    private String categorycode;
    private String categoryname;
    private int doctordxflag; //医生诊断标记
    private int fileviewflag;
    private JSONArray bizitemopt;
    private int needfileflag;
    private JSONObject controltype;

    public JSONArray getBizitemopt() {
        if(bizitemopt == null){
            return new JSONArray();
        }
        return bizitemopt;
    }

    public JSONObject getControltype() {
        if(controltype == null){
            return new JSONObject();
        }
        return controltype;
    }
}
