package com.platform.aix.common;

import com.alibaba.fastjson.JSONObject;
import com.platform.aix.PlatformAixApplicationTests;
import com.platform.aix.common.datacommon.db.domain.dict.DictEnums;
import com.platform.aix.common.datacommon.db.domain.dict.DictUtil;
import org.apache.commons.collections.SetUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:36
 */
public class DictTest extends PlatformAixApplicationTests {

    @Autowired
    private DictUtil dictUtil;
    @Test
    public void testGetDict(){
        String name = DictUtil.getName(DictEnums.Gender.MALE.getDictCode(), DictEnums.Gender.MALE.getItemCode());// 枚举
        String name1 = DictUtil.getName("ORDER_STATUS", "PAID");// 动态
        System.out.println(name+name1);
        DictEnums.Gender.MALE.getDictCode();
        Set<String>  set1 = new HashSet<>();
        set1.add("PACD");
        set1.add("CANCEL");
        dictUtil.getDictItems("YES_NO");
        System.out.println(JSONObject.toJSONString(DictUtil.batchGetItems("ORDER_STATUS",set1 )));
        System.out.println(JSONObject.toJSONString(DictUtil.getItem("GENDER","MALE")));
        System.out.println(JSONObject.toJSONString(DictUtil.getItem("ORDER_TYPE","NORMAL")));
        System.out.println(JSONObject.toJSONString(dictUtil.getDictItems("ORDER_TYPE")));
    }
}
