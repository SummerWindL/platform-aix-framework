package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.dict.DictItem;
import com.platform.aix.common.datacommon.db.domain.dict.DictType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月16日 15:05
 */
@Repository
public interface DictMapper {
    List<DictType> selectAllDictTypes();
    List<DictItem> selectDynamicItems();
    List<DictItem> selectItemsByDictCode(String dictCode);
}
