package com.platform.aix.service.user.inspection.adapter;

import com.platform.aix.cmd.cmd10010.Cmd10010Req;
import com.platform.aix.service.user.inspection.bean.DictInspectionBean;
import com.platform.aix.service.user.inspection.bean.InspectionDatas;
import com.platform.aix.service.user.inspection.convert.InspectionDataConvert;
import com.platform.aix.service.user.inspection.enums.EnumSonka2ZxInspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 11:22
 **/
@Component
public class InspectionAdapter {

//    @Autowired
//    private DictInspectionItemService inspectionItemService;

    private static List<DictInspectionBean> dictInspectionBeans = null;

    public static List<InspectionDatas> convertData(Cmd10010Req req){
        //不同的指标项做不同的处理
        InspectionDataConvert dataConvert = new InspectionDataConvert();
        return dataConvert.doconvert(req);
    }

    /**
     * 根据 inspectionindexname 过滤指标项
     * @return
     */
    public static DictInspectionBean filterInspectionItem(EnumSonka2ZxInspection e){
        return InspectionAdapter
                .dictInspectionBeans
                .stream()
                .filter(DictInspectionBean -> e.getValue().equals(DictInspectionBean.getInspectionindexname()))
                .collect(Collectors.toList()).get(0);
    }

    /**
     * 初始化类时 初始化 检测指标List 从数据库读取
     */
    @PostConstruct
    private void initInspectionItemList(){
        dictInspectionBeans = new ArrayList<DictInspectionBean>();
    }

}
