package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiTimelineImages;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineImagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PikaiTimelineImagesMapper extends IMybatisMapper<PikaiTimelineImages,Integer,PikaiTimelineImagesExample> {
    int countByExample(PikaiTimelineImagesExample example);

    int deleteByExample(PikaiTimelineImagesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PikaiTimelineImages record);

    int insertSelective(PikaiTimelineImages record);

    List<PikaiTimelineImages> selectByExample(PikaiTimelineImagesExample example);

    PikaiTimelineImages selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PikaiTimelineImages record, @Param("example") PikaiTimelineImagesExample example);

    int updateByExample(@Param("record") PikaiTimelineImages record, @Param("example") PikaiTimelineImagesExample example);

    int updateByPrimaryKeySelective(PikaiTimelineImages record);

    int updateByPrimaryKey(PikaiTimelineImages record);
}