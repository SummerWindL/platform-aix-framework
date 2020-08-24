package com.platform.aix.service.user.inspection.impl;

import com.alibaba.fastjson.JSONObject;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.util.JsonAdaptor;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.inspection.bean.Member;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-21 16:59
 **/
@Service
public class MemberService extends AbstractServiceImpl {
    @Autowired
    private JsonAdaptor jsonAdaptor ;
//    @Autowired
//    FMbSsidCertnumQueryRepository fMbSsidCertnumQueryRepository;

    public static Member member = null;


    /**
     * 初始化用户基本信息
     * @param
     */
    public  void getMemberInfo(JSONObject memberInfo) {
        try {
            //1.赋默认值
            member = jsonAdaptor.readValue(JSONObject.toJSONString(memberInfo), Member.class);
            querySsInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void querySsInfo(){
        String ssid = "";
        String hospcode = "";
        BasePlpgsqlModel basePlpgsqlModel = null;
//        if(null != member.getIdCode() && !"".equals(member.getIdCode())){
//            basePlpgsqlModel = fMbSsidCertnumQueryRepository.fMbSsidCertnumQuery(
//                    "admin",
//                    "01",
//                    "",
//                    "",
//                    String.valueOf(member.getIdCode()));
//            if (0 > basePlpgsqlModel.getRetcode()) {
//                throw new BIZException(ResponseResult.DB_ERROR);
//            }else if(basePlpgsqlModel.getRetcode() > 0){ //存在档案
//                JSONObject[] jsonObject = basePlpgsqlModel2Clz(basePlpgsqlModel, JSONObject[].class);
//                List<JSONObject> list = CollectionUtils.arrayToList(jsonObject);
//                if(!CollectionUtils.isEmpty(list)) {
//                    JSONObject obj = list.get(0);
//                    ssid = (String)obj.get("ssid"); //存在基本档案 ssid需要用原来第一份档案的ssid
//                    hospcode = (String)obj.get("hospcode");
//                }
//            }else if(basePlpgsqlModel.getRetcode() == 0){ //无档案
//                throw new BIZException(ResponseResult.BIZ_ERROR_SS_NOT_EXIST);
//            }
//        }
        member.setSsid(ssid);

    }


}
