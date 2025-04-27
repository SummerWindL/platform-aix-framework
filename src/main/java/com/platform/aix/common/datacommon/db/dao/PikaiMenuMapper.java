package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiMenu;
import com.platform.aix.common.datacommon.db.domain.PikaiMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiMenuMapper extends IMybatisMapper<PikaiMenu,Integer,PikaiMenuExample> {
    int countByExample(PikaiMenuExample example);

    int deleteByExample(PikaiMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PikaiMenu record);

    int insertSelective(PikaiMenu record);

    List<PikaiMenu> selectByExample(PikaiMenuExample example);

    PikaiMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PikaiMenu record, @Param("example") PikaiMenuExample example);

    int updateByExample(@Param("record") PikaiMenu record, @Param("example") PikaiMenuExample example);

    int updateByPrimaryKeySelective(PikaiMenu record);

    int updateByPrimaryKey(PikaiMenu record);
}