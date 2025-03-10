package com.platform.aix.common.datacommon.db.service;

import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.domain.PuzzleHistory;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月08日 13:18
 */
public interface PuzzleHistoryService extends AsyncService<Integer, PuzzleHistory> {
    PuzzleHistory queryById(Integer id);

   /**
    * 单个保存方法
    * @author fuyanliang
    * @date 2025/3/8 14:14
    * @param puzzleHistory
    */
    void savePuzzleHistory(PuzzleHistory puzzleHistory);

    /**
     * 插入返回实体
     * @author fuyanliang
     * @date 2025/3/8 16:25
     * @param puzzleHistory
     * @return com.platform.aix.common.datacommon.db.domain.PuzzleHistory
     */
    PuzzleHistory savePuzzleHistoryReturnEntity(PuzzleHistory puzzleHistory);

    void savePuzzleHistory(List<PuzzleHistory> puzzleHistoryList);

    List<PuzzleHistory> queryAll();


    PuzzleHistory getOnePuzzleHistory(PuzzleHistory puzzleHistory);

    /**
     * 删除全部记录
     * @author fuyanliang
     * @date 2025/3/8 15:26
     * @return int
     */
    int deleteAll();

    /**
     * 单个历史记录删除
     * @author fuyanliang
     * @date 2025/3/8 15:31
     * @param id
     * @return int
     */
    int deleteHistory(Integer id);
}
