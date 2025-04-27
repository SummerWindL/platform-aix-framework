package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiTimelineContentMapper extends IMybatisMapper<PikaiTimelineContent,Integer,PikaiTimelineContentExample>{
    int countByExample(PikaiTimelineContentExample example);

    int deleteByExample(PikaiTimelineContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PikaiTimelineContent record);

    int insertSelective(PikaiTimelineContent record);

    List<PikaiTimelineContent> selectByExample(PikaiTimelineContentExample example);

    PikaiTimelineContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PikaiTimelineContent record, @Param("example") PikaiTimelineContentExample example);

    int updateByExample(@Param("record") PikaiTimelineContent record, @Param("example") PikaiTimelineContentExample example);

    int updateByPrimaryKeySelective(PikaiTimelineContent record);

    int updateByPrimaryKey(PikaiTimelineContent record);
}