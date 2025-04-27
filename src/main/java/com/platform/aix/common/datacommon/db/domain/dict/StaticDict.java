package com.platform.aix.common.datacommon.db.domain.dict;

import java.util.*;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 18:17
 * 统一字典枚举（静态字典集中管理）
 */
public enum StaticDict {
    // 性别字典
    GENDER_MALE("GENDER", "MALE", "男", "1", 1),
    GENDER_FEMALE("GENDER", "FEMALE", "女", "2", 2),

    // 是否字典
    YES_NO_YES("YES_NO", "YES", "是", "0", 1),
    YES_NO_NO("YES_NO", "NO", "否", "1", 2),

    // 其他静态字典...
    ORDER_TYPE_NORMAL("ORDER_TYPE", "NORMAL", "普通订单", "1", 1),
    ORDER_TYPE_GROUP("ORDER_TYPE", "GROUP", "团购订单", "2", 2);

    private final String dictCode;
    private final String itemCode;
    private final String itemName;
    private final String itemValue;
    private final int sort;

    // 多级缓存结构
    private static final Map<String, Map<String, StaticDict>> CODE_CACHE = new HashMap<>();
    private static final Map<String, List<StaticDict>> DICT_LIST = new HashMap<>();

    static {
        // 初始化缓存
        Arrays.stream(values()).forEach(e -> {
            // 字典代码 -> 项代码 -> 枚举
            CODE_CACHE.computeIfAbsent(e.dictCode, k -> new HashMap<>())
                    .put(e.itemCode, e);

            // 字典代码 -> 排序列表
            DICT_LIST.computeIfAbsent(e.dictCode, k -> new ArrayList<>())
                    .add(e);
        });

        // 对每个字典项排序
        DICT_LIST.values().forEach(list ->
                list.sort(Comparator.comparingInt(StaticDict::getSort))
        );
    }

    StaticDict(String dictCode, String itemCode,
               String itemName, String itemValue, int sort) {
        this.dictCode = dictCode;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.sort = sort;
    }

    // 通用查找方法
    public static Optional<StaticDict> findByCode(String dictCode, String itemCode) {
        return Optional.ofNullable(CODE_CACHE.get(dictCode))
                .map(m -> m.get(itemCode));
    }

    public static List<StaticDict> getDictItems(String dictCode) {
        return Collections.unmodifiableList(
                DICT_LIST.getOrDefault(dictCode, Collections.emptyList())
        );
    }

    // 获取字典项名称（安全空值处理）
    public static String getName(String dictCode, String itemCode) {
        return findByCode(dictCode, itemCode)
                .map(StaticDict::getItemName)
                .orElse("");
    }

    // Getters
    public String getDictCode() { return dictCode; }
    public String getItemCode() { return itemCode; }
    public String getItemName() { return itemName; }
    public String getItemValue() { return itemValue; }
    public int getSort() { return sort; }
}
