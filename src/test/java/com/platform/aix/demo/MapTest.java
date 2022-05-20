package com.platform.aix.demo;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author Advance
 * @date 2022年05月17日 10:30
 * @since V1.0.0
 */
public class MapTest {
    public static void main(String[] args) {

        if(false){
            System.out.println(111);
        }else{
            System.out.println(222);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("finprodType2","");
        map.put("test2",new Date());
        //死循环？？？
        IterableMap<String, Object> stringObjectIterableMap = MapUtils.iterableMap(map);
//        Set<Map.Entry<String, Object>> entries = stringObjectIterableMap.entrySet();
//        if(ObjectUtils.isEmpty(map.get("finprodType2"))){
//            while(entries.iterator().hasNext()){
//                Map.Entry<String, Object> next = entries.iterator().next();
//                if(StringUtils.equals(next.getKey(),"finprodType2")){
//                    next.setValue("F01");
//                }
//            }
//        }

        if(ObjectUtils.isEmpty(map.get("finprodType2"))) {
//            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, Object> next = iterator.next();
//                if (StringUtils.equals(next.getKey(), "finprodType2")) {
//                    next.setValue("F01");
//                }
//            }
            map.put("finprodType2","F01");
        }

        System.out.println(JSONObject.toJSONString(map));

    }
}
