package com.platform.aix.common.datacommon.db.dao;

import com.platform.aix.common.datacommon.db.domain.PuzzleHistory;
import com.platform.aix.common.datacommon.db.domain.PuzzleHistoryExample;
import java.util.List;

import com.platform.aix.common.datacommon.db.domain.User;
import com.platform.aix.common.datacommon.db.domain.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("puzzleHistoryMapper")
public interface PuzzleHistoryMapper extends IMybatisMapper<PuzzleHistory, Integer, PuzzleHistoryExample> {
    int countByExample(PuzzleHistoryExample example);

    int deleteByExample(PuzzleHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PuzzleHistory record);

    /**
     * 插入返回实体
     * @author fuyanliang
     * @date 2025/3/8 16:23
     * @param record
     * @return com.platform.aix.common.datacommon.db.domain.PuzzleHistory
     */
    PuzzleHistory insertReturnEntity(PuzzleHistory record);

    int insertSelective(PuzzleHistory record);

    List<PuzzleHistory> selectByExample(PuzzleHistoryExample example);

    PuzzleHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PuzzleHistory record, @Param("example") PuzzleHistoryExample example);

    int updateByExample(@Param("record") PuzzleHistory record, @Param("example") PuzzleHistoryExample example);

    int updateByPrimaryKeySelective(PuzzleHistory record);

    int updateByPrimaryKey(PuzzleHistory record);
}