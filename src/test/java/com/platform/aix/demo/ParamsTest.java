package com.platform.aix.demo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Advance
 * @date 2025年01月22日 13:05
 * @since V1.0.0
 */
public class ParamsTest {
    public static void main(String[] args) {
        System.out.println(wrapRequestParams(null));
        System.out.println(JSON.parseObject(JSON.toJSONString(wrapRequestParams(null).get("params"))));
    }
    public static Map<String, Object> wrapRequestParams(Map<String, Object> param) {
        // 构建date数组
        ArrayList<String> dateList = new ArrayList<>();
        dateList.add("2024-08-30");
        dateList.add("2024-08-30");

        // 构建businessCode数组（空）
        ArrayList<String> businessCodeList = new ArrayList<>();

        // 构建page对象
        Map<String, Object> page = new HashMap<>();
        page.put("pageNo", 1);
        page.put("pageSize", 15);
        page.put("sort", null);
        page.put("direction", null);

        // 构建params对象
        Map<String, Object> params = new HashMap<>();
        params.put("ownedModuleid", "undoList");
        params.put("ccid", "c8f425da52c84bc68f0ed5f3364b7efa");
        params.put("date", dateList);
        params.put("businessCode", businessCodeList);

        // 构建整个请求参数
        Map<String, Object> requestParameters = new HashMap<>();
        requestParameters.put("params", params);
        requestParameters.put("pageId", "UnhandledEvent001");
        requestParameters.put("page", page);
        requestParameters.put("isTodoList", false);
        requestParameters.put("todoListFlag", false);
        return requestParameters;
    }
}
