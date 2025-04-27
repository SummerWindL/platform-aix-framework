package com.platform.aix.common.datacommon.db.domain.dict;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:16
 */
public interface DictEnumInterf {
    String getDictCode();
    String getItemCode();
    String getItemName();
    String getItemValue();
    int getSortOrder();

    /**
     * 通用查找方法（静态方法需通过伴随类实现）
     */
    class Finder {
        private static final Map<String, Map<String, DictEnumInterf>> CACHE = new HashMap<>();

        static {
            // 自动发现所有字典枚举
            ServiceLoader<DictEnumProvider> loader = ServiceLoader.load(DictEnumProvider.class);
            loader.forEach(provider ->
                    provider.provideEnums().forEach(Finder::cacheEnum)
            );
        }

        private static void cacheEnum(DictEnumInterf e) {
            CACHE.computeIfAbsent(e.getDictCode(), k -> new HashMap<>())
                    .put(e.getItemCode(), e);
        }

        public static Optional<DictEnumInterf> find(String dictCode, String itemCode) {
            return Optional.ofNullable(CACHE.get(dictCode))
                    .map(m -> m.get(itemCode));
        }

        public static List<DictEnumInterf> getDictItems(String dictCode) {
            return Optional.ofNullable(CACHE.get(dictCode))
                    .map(Map::values)
                    .map(list -> list.stream()
                            .sorted(Comparator.comparingInt(DictEnumInterf::getSortOrder))
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }
    }

    /**
     * 字典枚举提供者接口（SPI机制）
     */
    interface DictEnumProvider {
        Stream<? extends DictEnumInterf> provideEnums();
    }

    /**
     * 抽象基础枚举实现（减少重复代码）
     */
    class BaseDictEnum implements DictEnumInterf {
        private final String dictCode;
        private final String itemCode;
        private final String itemName;
        private final String itemValue;
        private final int sortOrder;

        protected BaseDictEnum(String dictCode, String itemCode,
                               String itemName, String itemValue, int sortOrder) {
            this.dictCode = Objects.requireNonNull(dictCode);
            this.itemCode = Objects.requireNonNull(itemCode);
            this.itemName = Objects.requireNonNull(itemName);
            this.itemValue = Objects.requireNonNull(itemValue);
            this.sortOrder = sortOrder;
        }

        @Override public String getDictCode() { return dictCode; }
        @Override public String getItemCode() { return itemCode; }
        @Override public String getItemName() { return itemName; }
        @Override public String getItemValue() { return itemValue; }
        @Override public int getSortOrder() { return sortOrder; }

        @Override
        public String toString() {
            return String.format("%s[%s]", dictCode, itemCode);
        }
    }
}
