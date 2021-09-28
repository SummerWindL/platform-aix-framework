package com.platform.aix.service.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import net.sf.json.JSONArray;

import java.io.IOException;

/**
 * @description:
 */
public interface TreeJsonService {

    /**
     * 查询指定省份下，所有市区县的树状菜单列表
     *
     * @return 以符合VUE.js sidebar-menu展示风格的嵌套JSONArray方式返回
     */
    JSONArray queryAreacodeList(String areacode);

    /**
     * 查询指定省份下，所有市区县及其所辖医疗机构的树状菜单列表
     *
     * @return 以符合VUE.js sidebar-menu展示风格的嵌套JSONArray方式返回
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    BasePlpgsqlModel queryAreacodeHospList(String areacode) throws JsonParseException, JsonMappingException, IOException;

    /**
     * 查询指定省份下，所有市区县及其所辖医疗机构字典的树状菜单列表
     *
     * @return 以符合VUE.js sidebar-menu展示风格的嵌套JSONArray方式返回
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    BasePlpgsqlModel queryAreacodeHospDictList(String areacode) throws JsonParseException, JsonMappingException, IOException;
}
