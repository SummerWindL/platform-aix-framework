/*
 * 文件名：MapperDelegateSqlProvider.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：杨智
 * 修改时间：2018年10月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.platform.aix.service.common.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapperDelegateSqlProvider {

    @SuppressWarnings("unchecked")
	public String deleteBySpecificKey(Map<String, Object> param) {
        SQL sql = new SQL().DELETE_FROM((String)param.get("tableName"));
        Map<String, Object> conditions = (Map<String, Object>)param.get("conditions");
        List<String> conditionList = conditions.entrySet().stream().map(
            entry -> entry.getKey() + " = '" + entry.getValue() + "'").collect(
                Collectors.toList());
        sql.WHERE(conditionList.toArray(new String[conditionList.size()]));
        return sql.toString();
    }
}
