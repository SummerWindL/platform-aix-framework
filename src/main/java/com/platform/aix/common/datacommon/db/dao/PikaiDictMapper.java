package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiDict;
import com.platform.aix.common.datacommon.db.domain.PikaiDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PikaiDictMapper {
    int countByExample(PikaiDictExample example);

    int deleteByExample(PikaiDictExample example);

    int insert(PikaiDict record);

    int insertSelective(PikaiDict record);

    List<PikaiDict> selectByExample(PikaiDictExample example);

    int updateByExampleSelective(@Param("record") PikaiDict record, @Param("example") PikaiDictExample example);

    int updateByExample(@Param("record") PikaiDict record, @Param("example") PikaiDictExample example);
}