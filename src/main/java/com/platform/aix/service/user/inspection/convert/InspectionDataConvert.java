package com.platform.aix.service.user.inspection.convert;

import com.alibaba.fastjson.JSONObject;
import com.platform.aix.cmd.cmd10010.Cmd10010Req;
import com.platform.aix.service.user.inspection.bean.DictInspectionBean;
import com.platform.aix.service.user.inspection.bean.InspectionCommonBean;
import com.platform.aix.service.user.inspection.bean.InspectionDatas;
import com.platform.aix.service.user.inspection.enums.EnumSonka2ZxInspection;
import com.platform.aix.service.user.inspection.impl.MemberService;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.platform.aix.service.user.inspection.adapter.InspectionAdapter.filterInspectionItem;

/**
 * @description: 双佳数据转换为竹信检测记录插入检测表中
 * @author: fuyl
 * @create: 2020-08-21 14:09
 **/
@Component
public class InspectionDataConvert extends MemberService {

    private InspectionCommonBean inspectionCommonBean = new InspectionCommonBean();
    /**
     * 数据转换方法
     * @param req
     * @return
     */
    public List<InspectionDatas> doconvert(Cmd10010Req req){
        if(ObjectUtils.isEmpty(req)){
            return null;
        }
        //初始化外部结构体
        BeanUtils.copyProperties(req,inspectionCommonBean);
        List<InspectionDatas> a = convertHight(req.getHeight());
        List<InspectionDatas> b = convertFat(req.getFat());
//        List<InspectionDatas> c = convertMinFat(req.getMinFat());
        List<InspectionDatas> d = convertBloodPressure(req.getBloodPressure());
        List<InspectionDatas> e = convertBo(req.getBo());
        List<InspectionDatas> f = convertEcg(req.getEcg());
        List<InspectionDatas> g = convertPEEcg(req.getPEEcg());
        List<InspectionDatas> h = convertTemperature(req.getTemperature());
        List<InspectionDatas> i = convertWhr(req.getWhr());
        List<InspectionDatas> j = convertBloodSugar(req.getBloodSugar());
        List<InspectionDatas> k = convertUa(req.getUa());
        List<InspectionDatas> l = convertChol(req.getChol());
        List<InspectionDatas> m = convertBloodFat(req.getBloodFat());
//        List<InspectionDatas> n = convertCardiovascular(req.getCardiovascular());
//        List<InspectionDatas> o = convertBMD(req.getBMD());
        List<InspectionDatas> p = convertHb(req.getHb());
//        List<InspectionDatas> q = convertAlcohol(req.getAlcohol());
        List<InspectionDatas> r = convertLung(req.getLung());
        List<InspectionDatas> s = convertUrinalysis(req.getUrinalysis());
//        List<InspectionDatas> t = convertArteryAve(req.getArteryAve());
        List<InspectionDatas> list = CollectionUtils.arrayToList(concatAll(a.toArray(),b.toArray(),
//                c.toArray(),
                d.toArray(),e.toArray(),f.toArray(),g.toArray(),
                h.toArray(),i.toArray(),j.toArray(),k.toArray(),
                l.toArray(),m.toArray(),
//                ,n.toArray(),o.toArray(),
                p.toArray(),
//                ,q.toArray(),
                r.toArray(),s.toArray()
//                ,t.toArray()
                )
        );
        return list;
    }


    /**
     * 兆和动脉硬化
     * @param arteryAve
     */
    private List<InspectionDatas> convertArteryAve(JSONObject arteryAve) {
        return null;
    }

    /**
     * 尿液分析
     * @param urinalysis
     */
    private List<InspectionDatas> convertUrinalysis(JSONObject urinalysis) {
        //判空
        if(ObjectUtils.isEmpty(urinalysis)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //尿潜血  竹信这三项是定性数据 双佳定量数据 暂时不上传这个数据
        /*DictInspectionBean bldBean = filterInspectionItem(EnumSonka2ZxInspection.E00038);
        String bld = String.valueOf(urinalysis.get("BLD"));
        list.add(commonConstructorInspectionDatas(bldBean.getInspectionitemcode(),bldBean.getInspectionitemname()
                ,bldBean.getInspectionsubitemcode(),bldBean.getInspectionsubitemname()
                ,bldBean.getInspectionindexcode(),bldBean.getInspectionindexname()
                ,bldBean.getInspectionindextype()
                ,bld,"",""));
        //尿酮体
        DictInspectionBean ketBean = filterInspectionItem(EnumSonka2ZxInspection.E00039);
        String ket = String.valueOf(urinalysis.get("KET"));
        list.add(commonConstructorInspectionDatas(ketBean.getInspectionitemcode(),ketBean.getInspectionitemname()
                ,ketBean.getInspectionsubitemcode(),ketBean.getInspectionsubitemname()
                ,ketBean.getInspectionindexcode(),ketBean.getInspectionindexname()
                ,ketBean.getInspectionindextype()
                ,ket,"",""));
        //尿蛋白
        DictInspectionBean proBean = filterInspectionItem(EnumSonka2ZxInspection.E00040);
        String pro = String.valueOf(urinalysis.get("PRO"));
        list.add(commonConstructorInspectionDatas(proBean.getInspectionitemcode(),proBean.getInspectionitemname()
                ,proBean.getInspectionsubitemcode(),proBean.getInspectionsubitemname()
                ,proBean.getInspectionindexcode(),proBean.getInspectionindexname()
                ,proBean.getInspectionindextype()
                ,pro,"",""));*/
        //尿微量白蛋白
        DictInspectionBean malBean = filterInspectionItem(EnumSonka2ZxInspection.E00041);
        String mal = String.valueOf(urinalysis.get("MAL"));
        list.add(commonConstructorInspectionDatas(malBean.getInspectionitemcode(),malBean.getInspectionitemname()
                ,malBean.getInspectionsubitemcode(),malBean.getInspectionsubitemname()
                ,malBean.getInspectionindexcode(),malBean.getInspectionindexname()
                ,malBean.getInspectionindextype()
                ,mal,"",""));
        return list;
    }

    /**
     * 肺活量
     * @param lung
     */
    private List<InspectionDatas> convertLung(JSONObject lung) {
        //判空
        if(ObjectUtils.isEmpty(lung)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //FVC
        DictInspectionBean fvcBean = filterInspectionItem(EnumSonka2ZxInspection.E00035);
        String fvc = String.valueOf(lung.get("FVC"));
        list.add(commonConstructorInspectionDatas(fvcBean.getInspectionitemcode(),fvcBean.getInspectionitemname()
                ,fvcBean.getInspectionsubitemcode(),fvcBean.getInspectionsubitemname()
                ,fvcBean.getInspectionindexcode(),fvcBean.getInspectionindexname()
                ,fvcBean.getInspectionindextype()
                ,fvc,"",""));
        //FEV1
        DictInspectionBean fev1Bean = filterInspectionItem(EnumSonka2ZxInspection.E00036);
        String fev1 = String.valueOf(lung.get("FEV1"));
        list.add(commonConstructorInspectionDatas(fev1Bean.getInspectionitemcode(),fev1Bean.getInspectionitemname()
                ,fev1Bean.getInspectionsubitemcode(),fev1Bean.getInspectionsubitemname()
                ,fev1Bean.getInspectionindexcode(),fev1Bean.getInspectionindexname()
                ,fev1Bean.getInspectionindextype()
                ,fev1,"",""));
        //PEF
        DictInspectionBean pefBean = filterInspectionItem(EnumSonka2ZxInspection.E00037);
        String pef = String.valueOf(lung.get("PEF"));
        list.add(commonConstructorInspectionDatas(pefBean.getInspectionitemcode(),pefBean.getInspectionitemname()
                ,pefBean.getInspectionsubitemcode(),pefBean.getInspectionsubitemname()
                ,pefBean.getInspectionindexcode(),pefBean.getInspectionindexname()
                ,pefBean.getInspectionindextype()
                ,pef,"",""));
        return list;
    }

    /**
     * 酒精浓度
     * @param alcohol
     */
    private List<InspectionDatas> convertAlcohol(JSONObject alcohol) {
        return null;
    }

    /**
     * 血红蛋白
     * @param hb
     */
    private List<InspectionDatas> convertHb(JSONObject hb) {
        //判空
        if(ObjectUtils.isEmpty(hb)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //血红蛋白
        DictInspectionBean hbBean = filterInspectionItem(EnumSonka2ZxInspection.E00033);
        String hbData = (String) hb.get("Hb");
        list.add(commonConstructorInspectionDatas(hbBean.getInspectionitemcode(),hbBean.getInspectionitemname()
                ,hbBean.getInspectionsubitemcode(),hbBean.getInspectionsubitemname()
                ,hbBean.getInspectionindexcode(),hbBean.getInspectionindexname()
                ,hbBean.getInspectionindextype()
                ,hbData,"",""));

        //血细胞比容
        DictInspectionBean hctBean = filterInspectionItem(EnumSonka2ZxInspection.E00034);
        String hct = (String) hb.get("Hct");
        list.add(commonConstructorInspectionDatas(hctBean.getInspectionitemcode(),hctBean.getInspectionitemname()
                ,hctBean.getInspectionsubitemcode(),hctBean.getInspectionsubitemname()
                ,hctBean.getInspectionindexcode(),hctBean.getInspectionindexname()
                ,hctBean.getInspectionindextype()
                ,hct,"",""));

        return list;
    }

    /**
     * 骨密度
     * @param bmd
     */
    private List<InspectionDatas> convertBMD(JSONObject bmd) {
        return null;
    }

    /**
     * 心血管
     * @param cardiovascular
     */
    private List<InspectionDatas> convertCardiovascular(JSONObject cardiovascular) {
        return null;
    }

    /**
     * 血脂
     * @param bloodFat
     */
    private List<InspectionDatas> convertBloodFat(JSONObject bloodFat) {
        //判空
        if(ObjectUtils.isEmpty(bloodFat)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //高密度脂蛋白
        DictInspectionBean hdlCholBean = filterInspectionItem(EnumSonka2ZxInspection.E00030);
        String hdlChol = (String) bloodFat.get("HdlChol");
        list.add(commonConstructorInspectionDatas(hdlCholBean.getInspectionitemcode(),hdlCholBean.getInspectionitemname()
                ,hdlCholBean.getInspectionsubitemcode(),hdlCholBean.getInspectionsubitemname()
                ,hdlCholBean.getInspectionindexcode(),hdlCholBean.getInspectionindexname()
                ,hdlCholBean.getInspectionindextype()
                ,hdlChol,"",""));

        //甘油三酯
        DictInspectionBean trigBean = filterInspectionItem(EnumSonka2ZxInspection.E00031);
        String trig = (String) bloodFat.get("Trig");
        list.add(commonConstructorInspectionDatas(trigBean.getInspectionitemcode(),trigBean.getInspectionitemname()
                ,trigBean.getInspectionsubitemcode(),trigBean.getInspectionsubitemname()
                ,trigBean.getInspectionindexcode(),trigBean.getInspectionindexname()
                ,trigBean.getInspectionindextype()
                ,trig,"",""));

        //低密度脂蛋白
        DictInspectionBean calcLdlBean = filterInspectionItem(EnumSonka2ZxInspection.E00032);
        String calcLdl = (String) bloodFat.get("CalcLdl");
        list.add(commonConstructorInspectionDatas(calcLdlBean.getInspectionitemcode(),calcLdlBean.getInspectionitemname()
                ,calcLdlBean.getInspectionsubitemcode(),calcLdlBean.getInspectionsubitemname()
                ,calcLdlBean.getInspectionindexcode(),calcLdlBean.getInspectionindexname()
                ,calcLdlBean.getInspectionindextype()
                ,calcLdl,"",""));

        return list;
    }

    /**
     * 总胆固醇
     * @param chol
     */
    private List<InspectionDatas> convertChol(JSONObject chol) {
        //判空
        if(ObjectUtils.isEmpty(chol)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //总胆固醇
        DictInspectionBean cholBean = filterInspectionItem(EnumSonka2ZxInspection.E00029);
        String cholData = (String) chol.get("Chol");
        list.add(commonConstructorInspectionDatas(cholBean.getInspectionitemcode(),cholBean.getInspectionitemname()
                ,cholBean.getInspectionsubitemcode(),cholBean.getInspectionsubitemname()
                ,cholBean.getInspectionindexcode(),cholBean.getInspectionindexname()
                ,cholBean.getInspectionindextype()
                ,cholData,"",""));
        return list;
    }

    /**
     * 尿酸
     * @param ua
     */
    private List<InspectionDatas> convertUa(JSONObject ua) {
        //判空
        if(ObjectUtils.isEmpty(ua)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //尿酸
        DictInspectionBean uaBean = filterInspectionItem(EnumSonka2ZxInspection.E00028);
        String uaData = (String) ua.get("Ua");
        list.add(commonConstructorInspectionDatas(uaBean.getInspectionitemcode(),uaBean.getInspectionitemname()
                ,uaBean.getInspectionsubitemcode(),uaBean.getInspectionsubitemname()
                ,uaBean.getInspectionindexcode(),uaBean.getInspectionindexname()
                ,uaBean.getInspectionindextype()
                ,uaData,"",""));
        return list;
    }

    /**
     * 血糖
     * @param bloodSugar
     */
    private List<InspectionDatas> convertBloodSugar(JSONObject bloodSugar) {
        //判空
        if(ObjectUtils.isEmpty(bloodSugar)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        String bloodSugarType = (String) bloodSugar.get("BloodsugarType");
        if("1".equals(bloodSugarType)){
            //空腹血糖
            DictInspectionBean bloodSugarBean = filterInspectionItem(EnumSonka2ZxInspection.E00025);
            String bloodSugar1 = (String) bloodSugar.get("BloodSugar");
            list.add(commonConstructorInspectionDatas(bloodSugarBean.getInspectionitemcode(),bloodSugarBean.getInspectionitemname()
                    ,bloodSugarBean.getInspectionsubitemcode(),bloodSugarBean.getInspectionsubitemname()
                    ,bloodSugarBean.getInspectionindexcode(),bloodSugarBean.getInspectionindexname()
                    ,bloodSugarBean.getInspectionindextype()
                    ,bloodSugar1,"",""));
        }else if("2".equals(bloodSugarType)){
            //餐后血糖
            DictInspectionBean bloodSugarBean = filterInspectionItem(EnumSonka2ZxInspection.E00026);
            String bloodSugar2 = (String) bloodSugar.get("BloodSugar");
            list.add(commonConstructorInspectionDatas(bloodSugarBean.getInspectionitemcode(),bloodSugarBean.getInspectionitemname()
                    ,bloodSugarBean.getInspectionsubitemcode(),bloodSugarBean.getInspectionsubitemname()
                    ,bloodSugarBean.getInspectionindexcode(),bloodSugarBean.getInspectionindexname()
                    ,bloodSugarBean.getInspectionindextype()
                    ,bloodSugar2,"",""));
        }else if("3".equals(bloodSugarType)){
            //随机血糖
            DictInspectionBean bloodSugarBean = filterInspectionItem(EnumSonka2ZxInspection.E00027);
            String bloodSugar3 = (String) bloodSugar.get("BloodSugar");
            list.add(commonConstructorInspectionDatas(bloodSugarBean.getInspectionitemcode(),bloodSugarBean.getInspectionitemname()
                    ,bloodSugarBean.getInspectionsubitemcode(),bloodSugarBean.getInspectionsubitemname()
                    ,bloodSugarBean.getInspectionindexcode(),bloodSugarBean.getInspectionindexname()
                    ,bloodSugarBean.getInspectionindextype()
                    ,bloodSugar3,"",""));
        }

        return list;
    }

    /**
     * 腰臀比
     * @param whr
     */
    private List<InspectionDatas> convertWhr(JSONObject whr) {
        //判空
        if(ObjectUtils.isEmpty(whr)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //腰围
        DictInspectionBean waistlineBean = filterInspectionItem(EnumSonka2ZxInspection.E00023);
        String waistline = (String) whr.get("Waistline");
        list.add(commonConstructorInspectionDatas(waistlineBean.getInspectionitemcode(),waistlineBean.getInspectionitemname()
                ,waistlineBean.getInspectionsubitemcode(),waistlineBean.getInspectionsubitemname()
                ,waistlineBean.getInspectionindexcode(),waistlineBean.getInspectionindexname()
                ,waistlineBean.getInspectionindextype()
                ,waistline,"",""));

        //腰臀比
        DictInspectionBean whrBean = filterInspectionItem(EnumSonka2ZxInspection.E00024);
        String whrData = (String) whr.get("Whr");
        list.add(commonConstructorInspectionDatas(whrBean.getInspectionitemcode(),whrBean.getInspectionitemname()
                ,whrBean.getInspectionsubitemcode(),whrBean.getInspectionsubitemname()
                ,whrBean.getInspectionindexcode(),whrBean.getInspectionindexname()
                ,whrBean.getInspectionindextype()
                ,whrData,"",""));
        return list;
    }

    /**
     * 体温
     * @param temperature
     */
    private List<InspectionDatas> convertTemperature(JSONObject temperature) {
        //判空
        if(ObjectUtils.isEmpty(temperature)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //体温
        DictInspectionBean temperatureBean = filterInspectionItem(EnumSonka2ZxInspection.E00022);
        String temperatureData = (String) temperature.get("Temperature");
        list.add(commonConstructorInspectionDatas(temperatureBean.getInspectionitemcode(),temperatureBean.getInspectionitemname()
                ,temperatureBean.getInspectionsubitemcode(),temperatureBean.getInspectionsubitemname()
                ,temperatureBean.getInspectionindexcode(),temperatureBean.getInspectionindexname()
                ,temperatureBean.getInspectionindextype()
                ,temperatureData,"",""));

        return list;
    }

    /**
     * 12导心电图
     * @param peEcg
     */
    private List<InspectionDatas> convertPEEcg(JSONObject peEcg) {
        //判空
        if(ObjectUtils.isEmpty(peEcg)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //12导心电图
        DictInspectionBean peEcgBean = filterInspectionItem(EnumSonka2ZxInspection.E00021);
        String peEcgData = (String) peEcg.get("EcgData");
        list.add(commonConstructorInspectionDatas(peEcgBean.getInspectionitemcode(),peEcgBean.getInspectionitemname()
                ,peEcgBean.getInspectionsubitemcode(),peEcgBean.getInspectionsubitemname()
                ,peEcgBean.getInspectionindexcode(),peEcgBean.getInspectionindexname()
                ,peEcgBean.getInspectionindextype()
                ,peEcgData,"",""));
        return list;
    }

    /**
     * 单导心电图
     * @param ecg
     */
    private List<InspectionDatas> convertEcg(JSONObject ecg) {
        //判空
        if(ObjectUtils.isEmpty(ecg)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //单导心电图
        DictInspectionBean ecgBean = filterInspectionItem(EnumSonka2ZxInspection.E00020);
        String ecgData = (String) ecg.get("EcgData");
        list.add(commonConstructorInspectionDatas(ecgBean.getInspectionitemcode(),ecgBean.getInspectionitemname()
                ,ecgBean.getInspectionsubitemcode(),ecgBean.getInspectionsubitemname()
                ,ecgBean.getInspectionindexcode(),ecgBean.getInspectionindexname()
                ,ecgBean.getInspectionindextype()
                ,ecgData,"",""));
        return list;
    }

    /**
     * 血氧
     * @param bo
     */
    private List<InspectionDatas> convertBo(JSONObject bo) {
        //判空
        if(ObjectUtils.isEmpty(bo)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //血氧
        DictInspectionBean oxygenBean = filterInspectionItem(EnumSonka2ZxInspection.E00018);
        String oxygen = (String) bo.get("Oxygen");
        list.add(commonConstructorInspectionDatas(oxygenBean.getInspectionitemcode(),oxygenBean.getInspectionitemname()
                ,oxygenBean.getInspectionsubitemcode(),oxygenBean.getInspectionsubitemname()
                ,oxygenBean.getInspectionindexcode(),oxygenBean.getInspectionindexname()
                ,oxygenBean.getInspectionindextype()
                ,oxygen,"",""));
        //脉率
        DictInspectionBean bpmBean = filterInspectionItem(EnumSonka2ZxInspection.E00019);
        String bpm = (String) bo.get("Bpm");
        list.add(commonConstructorInspectionDatas(bpmBean.getInspectionitemcode(),bpmBean.getInspectionitemname()
                ,bpmBean.getInspectionsubitemcode(),bpmBean.getInspectionsubitemname()
                ,bpmBean.getInspectionindexcode(),bpmBean.getInspectionindexname()
                ,bpmBean.getInspectionindextype()
                ,bpm,"",""));

        return list;
    }

    /**
     * 血压
     * @param bloodPressure
     */
    private List<InspectionDatas> convertBloodPressure(JSONObject bloodPressure) {
        //判空
        if(ObjectUtils.isEmpty(bloodPressure)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();
        //血压
        DictInspectionBean bloodPressureBean = filterInspectionItem(EnumSonka2ZxInspection.E00016);
        String highPressure = (String) bloodPressure.get("HighPressure"); //收缩压
        String lowPressure = (String) bloodPressure.get("LowPressure"); //舒张压
        list.add(commonConstructorInspectionDatas(bloodPressureBean.getInspectionitemcode(),bloodPressureBean.getInspectionitemname()
                ,bloodPressureBean.getInspectionsubitemcode(),bloodPressureBean.getInspectionsubitemname()
                ,bloodPressureBean.getInspectionindexcode(),bloodPressureBean.getInspectionindexname()
                ,bloodPressureBean.getInspectionindextype()
                ,highPressure,lowPressure,""));

        //心率
        DictInspectionBean pulseBean = filterInspectionItem(EnumSonka2ZxInspection.E00017);
        String pulse = (String) bloodPressure.get("Pulse");
        list.add(commonConstructorInspectionDatas(pulseBean.getInspectionitemcode(),pulseBean.getInspectionitemname()
                ,pulseBean.getInspectionsubitemcode(),pulseBean.getInspectionsubitemname()
                ,pulseBean.getInspectionindexcode(),pulseBean.getInspectionindexname()
                ,pulseBean.getInspectionindextype()
                ,pulse,"",""));
        return list;
    }

    /**
     * 人体成分 不处理
     * @param minFat
     */
    private List<InspectionDatas> convertMinFat(JSONObject minFat) {
        return null;
    }

    /**
     * 人体成分
     * @param fat
     */
    private List<InspectionDatas> convertFat(JSONObject fat) {
        //判空
        if(ObjectUtils.isEmpty(fat)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();

        //体脂率
        DictInspectionBean fatRateBean = filterInspectionItem(EnumSonka2ZxInspection.E00004);
        String fatRate = (String) fat.get("FatRate");
        list.add(commonConstructorInspectionDatas(fatRateBean.getInspectionitemcode()
                ,fatRateBean.getInspectionitemname(),fatRateBean.getInspectionsubitemcode(),fatRateBean.getInspectionsubitemname()
                ,fatRateBean.getInspectionindexcode(),fatRateBean.getInspectionindexname()
                ,fatRateBean.getInspectionindextype()
                ,fatRate,"",""));

        //内脏脂肪指数
        DictInspectionBean visceraBean = filterInspectionItem(EnumSonka2ZxInspection.E00005);
        String viscera = (String) fat.get("Viscera");
        list.add(commonConstructorInspectionDatas(visceraBean.getInspectionitemcode()
                ,visceraBean.getInspectionitemname(),visceraBean.getInspectionsubitemcode(),visceraBean.getInspectionsubitemname()
                ,visceraBean.getInspectionindexcode(),visceraBean.getInspectionindexname()
                ,visceraBean.getInspectionindextype()
                ,viscera,"",""));

        //基础代谢率
        DictInspectionBean basicMetabolismBean = filterInspectionItem(EnumSonka2ZxInspection.E00006);
        String basicMetabolism = (String) fat.get("BasicMetabolism");
        list.add(commonConstructorInspectionDatas(basicMetabolismBean.getInspectionitemcode(),basicMetabolismBean.getInspectionitemname()
                ,basicMetabolismBean.getInspectionsubitemcode(),basicMetabolismBean.getInspectionsubitemname()
                ,basicMetabolismBean.getInspectionindexcode(),basicMetabolismBean.getInspectionindexname()
                ,basicMetabolismBean.getInspectionindextype()
                ,basicMetabolism,"",""));

        //肌肉率
        DictInspectionBean muscleRateBean = filterInspectionItem(EnumSonka2ZxInspection.E00007);
        String muscleRate = (String) fat.get("MuscleRate");
        list.add(commonConstructorInspectionDatas(muscleRateBean.getInspectionitemcode(),muscleRateBean.getInspectionitemname()
                ,muscleRateBean.getInspectionsubitemcode(),muscleRateBean.getInspectionsubitemname()
                ,muscleRateBean.getInspectionindexcode(),muscleRateBean.getInspectionindexname()
                ,muscleRateBean.getInspectionindextype()
                ,muscleRate,"",""));

        // 水分率
        DictInspectionBean waterRateBean = filterInspectionItem(EnumSonka2ZxInspection.E00008);
        String waterRate = (String) fat.get("WaterRate");
        list.add(commonConstructorInspectionDatas(waterRateBean.getInspectionitemcode(),waterRateBean.getInspectionitemname()
                ,waterRateBean.getInspectionsubitemcode(),waterRateBean.getInspectionsubitemname()
                ,waterRateBean.getInspectionindexcode(),waterRateBean.getInspectionindexname()
                ,waterRateBean.getInspectionindextype()
                ,waterRate,"",""));
        //肌肉量
        DictInspectionBean quganMuscleBean = filterInspectionItem(EnumSonka2ZxInspection.E00009);
        String quganMuscle = (String) fat.get("QuganMuscle");
        list.add(commonConstructorInspectionDatas(quganMuscleBean.getInspectionitemcode(),quganMuscleBean.getInspectionitemname()
                ,quganMuscleBean.getInspectionsubitemcode(),quganMuscleBean.getInspectionsubitemname()
                ,quganMuscleBean.getInspectionindexcode(),quganMuscleBean.getInspectionindexname()
                ,quganMuscleBean.getInspectionindextype()
                ,quganMuscle,"",""));

        //细胞内液量
        DictInspectionBean ficBean = filterInspectionItem(EnumSonka2ZxInspection.E00010);
        String fic = (String) fat.get("Fic");
        list.add(commonConstructorInspectionDatas(ficBean.getInspectionitemcode(),ficBean.getInspectionitemname()
                ,ficBean.getInspectionsubitemcode(),ficBean.getInspectionsubitemname()
                ,ficBean.getInspectionindexcode(),ficBean.getInspectionindexname()
                ,ficBean.getInspectionindextype()
                ,fic,"",""));

        //细胞外液量
        DictInspectionBean focBean = filterInspectionItem(EnumSonka2ZxInspection.E00011);
        String foc = (String) fat.get("Foc");
        list.add(commonConstructorInspectionDatas(focBean.getInspectionitemcode(),focBean.getInspectionitemname()
                ,focBean.getInspectionsubitemcode(),focBean.getInspectionsubitemname()
                ,focBean.getInspectionindexcode(),focBean.getInspectionindexname()
                ,focBean.getInspectionindextype()
                ,foc,"",""));

        //脂肪量
        DictInspectionBean fatBean = filterInspectionItem(EnumSonka2ZxInspection.E00012);
        String fatData = (String) fat.get("Fat");
        list.add(commonConstructorInspectionDatas(fatBean.getInspectionitemcode(),fatBean.getInspectionitemname()
                ,fatBean.getInspectionsubitemcode(),fatBean.getInspectionsubitemname()
                ,fatBean.getInspectionindexcode(),fatBean.getInspectionindexname()
                ,fatBean.getInspectionindextype()
                ,fatData,"",""));

        //水分量
        DictInspectionBean waterBean = filterInspectionItem(EnumSonka2ZxInspection.E00013);
        String water = (String) fat.get("Water");
        list.add(commonConstructorInspectionDatas(waterBean.getInspectionitemcode(),waterBean.getInspectionitemname()
                ,waterBean.getInspectionsubitemcode(),waterBean.getInspectionsubitemname()
                ,waterBean.getInspectionindexcode(),waterBean.getInspectionindexname()
                ,waterBean.getInspectionindextype()
                ,water,"",""));

        //蛋白质
        DictInspectionBean proteinBean = filterInspectionItem(EnumSonka2ZxInspection.E00014);
        String protein = (String) fat.get("Protein");
        list.add(commonConstructorInspectionDatas(proteinBean.getInspectionitemcode(),proteinBean.getInspectionitemname()
                ,proteinBean.getInspectionsubitemcode(),proteinBean.getInspectionsubitemname()
                ,proteinBean.getInspectionindexcode(),proteinBean.getInspectionindexname()
                ,proteinBean.getInspectionindextype()
                ,protein,"",""));

        //骨质量
        DictInspectionBean bmcBean = filterInspectionItem(EnumSonka2ZxInspection.E00015);
        String bmc = (String) fat.get("Bmc");
        list.add(commonConstructorInspectionDatas(bmcBean.getInspectionitemcode(),bmcBean.getInspectionitemname()
                ,bmcBean.getInspectionsubitemcode(),bmcBean.getInspectionsubitemname()
                ,bmcBean.getInspectionindexcode(),bmcBean.getInspectionindexname()
                ,bmcBean.getInspectionindextype()
                ,bmc,"",""));

        return list;
    }

    /**
     * 通用构造检测数据结构体函数
     * @param itemcode
     * @param itemname
     * @param subitemcode
     * @param subitemname
     * @param indexcode
     * @param indexname
     * @param indextype
     * @param val1
     * @param val2
     * @param val3
     * @return
     */
    private InspectionDatas commonConstructorInspectionDatas(String itemcode,String itemname,
                                                                    String subitemcode,String subitemname,
                                                                    String indexcode,String indexname,
                                                                    Integer indextype,
                                                                    String val1,String val2,String val3){
        InspectionDatas inspectionDatas = new InspectionDatas();
        inspectionDatas.setInspectionitemcode(itemcode);
        inspectionDatas.setInspectionitemname(itemname);
        inspectionDatas.setInspectionsubitemcode(subitemcode);
        inspectionDatas.setInspectionsubitemname(subitemname);
        inspectionDatas.setInspectionindexcode(indexcode);
        inspectionDatas.setInspectionindexname(indexname);
        inspectionDatas.setInspectionindexvalue1(val1);
        inspectionDatas.setInspectionindexvalue2(val2);
        inspectionDatas.setInspectionindexvalue3(val3);
        inspectionDatas.setInspectionindextype(indextype);
        inspectionDatas.setInspectiontime(inspectionCommonBean.getMeasureTime());
        inspectionDatas.setHospcode(inspectionCommonBean.getUnitNo());
        inspectionDatas.setSsid(member.getSsid());
        return inspectionDatas;
    }

    /**
     * 身高体重
     * @param height
     */
    private  List<InspectionDatas> convertHight(JSONObject height) {
        //判空
        if(ObjectUtils.isEmpty(height)){
            return new ArrayList<InspectionDatas>();
        }
        List<InspectionDatas> list = new ArrayList<>();

        //1. 获取竹信身高实例
        DictInspectionBean heightBean = filterInspectionItem(EnumSonka2ZxInspection.E00001);

        //2. 组装竹信身高结构体
        String ht = (String) height.get("Height");
        list.add(commonConstructorInspectionDatas(heightBean.getInspectionitemcode()
                ,heightBean.getInspectionitemname(),heightBean.getInspectionsubitemcode(),heightBean.getInspectionsubitemname(),
                heightBean.getInspectionindexcode(),heightBean.getInspectionindexname(),heightBean.getInspectionindextype(),ht,"",""));


        //体重
        DictInspectionBean weightBean = filterInspectionItem(EnumSonka2ZxInspection.E00002);
        String weight = (String)height.get("Weight");
        list.add(commonConstructorInspectionDatas(weightBean.getInspectionitemcode()
                ,weightBean.getInspectionitemname(),weightBean.getInspectionsubitemcode(),weightBean.getInspectionsubitemname(),
                weightBean.getInspectionindexcode(),weightBean.getInspectionindexname(),weightBean.getInspectionindextype(),weight,"",""));

        //BMI
        DictInspectionBean bmiBean = filterInspectionItem(EnumSonka2ZxInspection.E00003);
        String bmi = (String) height.get("BMI");
        list.add(commonConstructorInspectionDatas(bmiBean.getInspectionitemcode()
                ,bmiBean.getInspectionitemname(),bmiBean.getInspectionsubitemcode(),bmiBean.getInspectionsubitemname(),
                bmiBean.getInspectionindexcode(),bmiBean.getInspectionindexname(),bmiBean.getInspectionindextype(),bmi,"",""));

        return list;
    }


    /**
     * 合并多个数组
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    private <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;

    }

    /**
     * 数组合并
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    private <T> T[] concat(T[] a, T[] b) {
        final int alen = a.length;
        final int blen = b.length;
        if (alen == 0) {
            return b;
        }
        if (blen == 0) {
            return a;
        }
        final T[] result = (T[]) java.lang.reflect.Array.
                newInstance(a.getClass().getComponentType(), alen + blen);
        System.arraycopy(a, 0, result, 0, alen);
        System.arraycopy(b, 0, result, alen, blen);
        return result;
    }

    public void main(String[] args) {
        List<InspectionDatas> list1 = new ArrayList<>();
        InspectionDatas inspectionDatas1 = new InspectionDatas();
        inspectionDatas1.setInspectionitemcode("1001");
        inspectionDatas1.setInspectionsubitemcode("1001");
        inspectionDatas1.setInspectionindexcode("1001");
        inspectionDatas1.setInspectionindexvalue1("180");
        InspectionDatas inspectionDatas2 = new InspectionDatas();
        inspectionDatas2.setInspectionitemcode("1001");
        inspectionDatas2.setInspectionsubitemcode("1002");
        inspectionDatas2.setInspectionindexcode("1001");
        inspectionDatas2.setInspectionindexvalue1("60");
        list1.add(inspectionDatas1);
        list1.add(inspectionDatas2);

        List<InspectionDatas> list2 = new ArrayList<>();
        InspectionDatas inspectionDatas3 = new InspectionDatas();
        inspectionDatas3.setInspectionitemcode("1001");
        inspectionDatas3.setInspectionsubitemcode("1005");
        inspectionDatas3.setInspectionindexcode("1001");
        inspectionDatas3.setInspectionindexvalue1("180");
        list2.add(inspectionDatas3);


        List<InspectionDatas> list3 = new ArrayList<>();
        InspectionDatas inspectionDatas4 = new InspectionDatas();
        inspectionDatas4.setInspectionitemcode("1001");
        inspectionDatas4.setInspectionsubitemcode("1060");
        inspectionDatas4.setInspectionindexcode("1001");
        inspectionDatas4.setInspectionindexvalue1("180");
        list3.add(inspectionDatas4);


        List<InspectionDatas> list = CollectionUtils.arrayToList(concatAll(list1.toArray(), list2.toArray(),list3.toArray()));
        System.out.println(JSONArray.fromObject(list).toString());
    }
}
