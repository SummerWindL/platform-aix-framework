package com.platform.aix.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Advance
 * @date 2024年09月27日 10:17
 * @since V1.0.0
 */
public class VATQuarterlySummary {

    public static void main(String[] args) throws JSONException {
        //[{"F16_JYXX01001":{"04":14337.160000000000}}]
        // 假设这是你的数据结构
        List<Map<String, Map<String, BigDecimal>>> data = Collections.unmodifiableList(
                Arrays.asList(
                        Collections.singletonMap("F16_JYXX01001",
                                Collections.singletonMap("someUnknownKey", new BigDecimal("14337.160000000000"))
                        ),
                        Collections.singletonMap("AnotherKey",
                                Collections.singletonMap("anotherUnknownKey", new BigDecimal("9999.990000000000"))
                        )
                )
        );

        // 指定要查找的外层键
        String outerKey = "F16_JYXX01001";

        // 提取与指定外层键相关联的内层Map的第一个值
        BigDecimal firstInnerValue = getFirstInnerValueForSpecificOuterKey(data, outerKey);

        // 输出结果
        if (firstInnerValue != null) {
            System.out.println("找到的与外层键'" + outerKey + "'相关联的内层Map的第一个值为: " + firstInnerValue);
        } else {
            System.out.println("未找到与外层键'" + outerKey + "'相关联的内层Map的值或内层Map为空。");
        }


    }

    public static BigDecimal getFirstInnerValueForSpecificOuterKey(List<Map<String, Map<String, BigDecimal>>> data, String outerKey) {
        for (Map<String, Map<String, BigDecimal>> outerMap : data) {
            if (outerMap.containsKey(outerKey)) {
                Map<String, BigDecimal> innerMap = outerMap.get(outerKey);
                if (!innerMap.isEmpty()) {
                    // 获取内层Map的第一个键值对（不关心键名）
                    Map.Entry<String, BigDecimal> innerEntry = innerMap.entrySet().iterator().next();
                    return innerEntry.getValue();
                }
            }
        }
        return null; // 如果没有找到，返回null
    }

    private static Map<Integer, Double> calculateQuarterlyVAT(String jsonInput) throws JSONException {
        // 解析JSON数据
        JSONArray jsonArray = new JSONArray(jsonInput);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONArray monthlyData = jsonObject.getJSONArray("F16_JYXX0608");

        // 创建一个Map来存储每个季度的增值税总额
        Map<Integer, Double> quarterlyVAT = new HashMap<>();
        double previousQuarterNegativeBalance = 0.0; // 上一季度的负数留底

        // 初始化季度增值税总额
        for (int i = 1; i <= 4; i++) {
            quarterlyVAT.put(i, 0.0);
        }

        // 遍历每个月的数据，计算每个季度的增值税总额
        for (int monthIndex = 0; monthIndex < monthlyData.length(); ) {
            double currentQuarterTotal = previousQuarterNegativeBalance; // 当前季度总额，初始值为上一季度的负数留底
            int currentQuarter = getQuarterFromMonth((String) monthlyData.getJSONObject(monthIndex).keys().next()); // 当前月份所在的季度

            // 累加当前季度内每个月的增值税额
            while (monthIndex < monthlyData.length() && getQuarterFromMonth((String) monthlyData.getJSONObject(monthIndex).keys().next()) == currentQuarter) {
                JSONObject monthlyRecord = monthlyData.getJSONObject(monthIndex);
                String month = (String) monthlyRecord.keys().next();
                double vat = monthlyRecord.getDouble(month);
                currentQuarterTotal += vat;
                monthIndex++;
            }

            // 判断当前季度总额是否为负数
            if (currentQuarterTotal < 0) {
                // 如果为负数，则留底到下一季度
                previousQuarterNegativeBalance = currentQuarterTotal;
                quarterlyVAT.put(currentQuarter, 0.0);
            } else {
                // 如果为正数或零，则清除留底，并设置当前季度的增值税总额
                quarterlyVAT.put(currentQuarter, currentQuarterTotal);
                previousQuarterNegativeBalance = 0.0;
            }
        }

        return quarterlyVAT;
    }

    // 辅助方法：根据月份字符串获取季度
    private static int getQuarterFromMonth(String month) {
        int monthNum = Integer.parseInt(month.substring(5, 7));
        return (monthNum - 1) / 3 + 1;
    }
}