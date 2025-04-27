package com.platform.aix.common.datacommon.db.domain.dict;

import com.platform.aix.common.datacommon.db.dao.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  使用方式：
 *  @Autowired
 *  private DictUtil dictUtil;
 *
 *  dictUtil.getName(DictEnums.Gender.MALE.getDictCode(), DictEnums.Gender.MALE.getItemCode())
 *  dictUtil.getItem("GENDER","MALE")
 *  Set<String>  set1 = new HashSet<>();
 *               set1.add("PACD");
 *               set1.add("CANCEL");
 *  dictUtil.batchGetItems("ORDER_STATUS",set1 )
 *  dictUtil.getDictItems("ORDER_TYPE") 18:34
 *
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:20
 */
@Component
public class DictUtil {
    @Autowired
    private DictMapper dictMapper;

    /**
     * 获取字典项
     * @author fuyanliang
     * @date 2025/4/16 18:31
     * @param dictCode
     * @param itemCode
     * @return com.platform.aix.common.datacommon.db.domain.dict.DictItem
     */
    public static DictItem getItem(String dictCode, String itemCode) {
        return DictCache.getItem(dictCode, itemCode);
    }

    /**
     * 获取字典项名称
     * @author fuyanliang
     * @date 2025/4/16 18:30
     * @param dictCode
     * @param itemCode
     * @return java.lang.String
     */
    public static String getName(String dictCode, String itemCode) {
        DictItem item = getItem(dictCode, itemCode);
        return item != null ? item.getItemName() : "";
    }

    /**
     * 获取整个字典项列表（优先枚举）
     * @author fuyanliang
     * @date 2025/4/16 18:30
     * @param dictCode
     * @return java.util.List<com.platform.aix.common.datacommon.db.domain.dict.DictItem>
     */
    public List<DictItem> getDictItems(String dictCode) {
        // 先查枚举
        Map<String, DictEnum> enumItems = DictCache.ENUM_CACHE.get(dictCode);
        if (enumItems != null) {
            return enumItems.values().stream()
                    .map(DictCache::convertEnumToItem)
                    .sorted(Comparator.comparingInt(DictItem::getSort))
                    .collect(Collectors.toList());
        }

        // 查动态缓存
        Map<String, DictItem> dynamicItems = DictCache.DYNAMIC_CACHE.get(dictCode);
        if (dynamicItems != null) {
            return dynamicItems.values().stream()
                    .sorted(Comparator.comparingInt(DictItem::getSort))
                    .collect(Collectors.toList());
        }

        // 兜底查数据库
        return dictMapper.selectItemsByDictCode(dictCode);
    }
    /**
     * 批量查询
     * @author fuyanliang
     * @date 2025/4/16 15:55
     * @param dictCode
     * @param itemCodes
     * @return java.util.Map<java.lang.String,com.platform.aix.common.datacommon.db.domain.dict.DictItem>
     */
    public static Map<String, DictItem> batchGetItems(String dictCode, Set<String> itemCodes) {
        return itemCodes.stream()
                .map(code -> getItem(dictCode, code))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(DictItem::getItemCode, Function.identity()));
    }
}
