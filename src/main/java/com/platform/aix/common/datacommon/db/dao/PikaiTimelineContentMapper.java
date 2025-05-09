package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContentExample;
import java.util.List;
import java.util.Map;

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

    /**
     * 更新 || 插入
     * @author fuyanliang
     * @date 2025/4/30 10:55
     * @param record
     * @return com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent
     */
    int updateOrInsertSelective(PikaiTimelineContent record);

    /**
     * 统计用户文章数量
     * @author fuyanliang
     * @date 2025/5/9 17:51
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    List<Map<String,Object>> countUserContentNum();
}