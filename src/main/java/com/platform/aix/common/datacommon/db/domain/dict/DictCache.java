package com.platform.aix.common.datacommon.db.domain.dict;

import com.platform.aix.common.datacommon.db.dao.DictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:19
 */
@Slf4j
@Component
public class DictCache {
    public static final Map<String, Map<String, DictEnum>> ENUM_CACHE = new ConcurrentHashMap<>();
    public static final Map<String, Map<String, DictItem>> DYNAMIC_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private DictMapper dictMapper;

    @PostConstruct
    public void init() {
        // 初始化枚举缓存
        cacheEnums(DictEnums.Gender.values());
        cacheEnums(DictEnums.YesNo.values());

        // 加载动态字典
        List<DictItem> dynamicItems = dictMapper.selectDynamicItems();
        dynamicItems.forEach(item -> {
            DYNAMIC_CACHE
                    .computeIfAbsent(item.getDictCode(), k -> new ConcurrentHashMap<>())
                    .put(item.getItemCode(), item);
        });
        log.info("=========字典加载完成===========");
    }

    private void cacheEnums(DictEnum[] enums) {
        Arrays.stream(enums).forEach(e -> {
            ENUM_CACHE
                    .computeIfAbsent(e.getDictCode(), k -> new ConcurrentHashMap<>())
                    .put(e.getItemCode(), e);
        });
    }

    public static DictItem getItem(String dictCode, String itemCode) {
        // 优先检查枚举
        DictEnum enumItem = Optional.ofNullable(ENUM_CACHE.get(dictCode))
                .map(m -> m.get(itemCode))
                .orElse(null);

        if (enumItem != null) {
            return convertEnumToItem(enumItem);
        }

        // 查询动态字典
        return Optional.ofNullable(DYNAMIC_CACHE.get(dictCode))
                .map(m -> m.get(itemCode))
                .orElse(null);
    }

    static DictItem convertEnumToItem(DictEnum e) {
        DictItem item = new DictItem();
        item.setDictCode(e.getDictCode());
        item.setItemCode(e.getItemCode());
        item.setItemName(e.getItemName());
        item.setItemValue(e.getItemValue());
        item.setSort(e.getSort());
        return item;
    }
}
