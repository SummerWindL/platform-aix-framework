package com.platform.aix.service.common.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.platform.aix.service.common.TreeJsonService;
import com.platform.common.util.TreeJsonUtil;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.dict.DictAreaCodeRepository;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 省市区县区域树状菜单
 *
 * @description:
 */
@Service
public class TreeJsonServiceImpl implements TreeJsonService {

    @Autowired
    private DictAreaCodeRepository dictAreaCodeRepository;

    @Override
    public JSONArray queryAreacodeList(String areacode) {
        BasePlpgsqlModel basePlpgsqlModel = this.dictAreaCodeRepository.queryDictAreacode(areacode);
        JSONArray areacodeJarr = new JSONArray();    //	定义要返回给前端使用的省市区县树状菜单JSONArray

        //	调用存储过程成功
        if (0 == basePlpgsqlModel.getRetcode()) {
            areacodeJarr = TreeJsonUtil.toAreacodeJson(basePlpgsqlModel.getRetvalue());
        } else {
            //	TODO 未处理
        }

        return areacodeJarr;
    }

    /**
     * 查询医疗机构地区树
     *
     * @param areacode
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Override
    public BasePlpgsqlModel queryAreacodeHospList(String areacode) throws JsonParseException, JsonMappingException, IOException {
        BasePlpgsqlModel basePlpgsqlModel = this.dictAreaCodeRepository.queryAreacodeHosp(areacode);
        return basePlpgsqlModel;
    }

    /**
     * 查询医疗机构字典地区树
     *
     * @param areacode
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Override
    public BasePlpgsqlModel queryAreacodeHospDictList(String areacode) throws JsonParseException, JsonMappingException, IOException {
        BasePlpgsqlModel basePlpgsqlModel = this.dictAreaCodeRepository.queryAreacodeHospTree(areacode);
        return basePlpgsqlModel;
    }

}
