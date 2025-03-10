package com.platform.aix.common.datacommon.db.service.impl;

import com.google.common.collect.Maps;
import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PuzzleHistoryMapper;
import com.platform.aix.common.datacommon.db.domain.PuzzleHistory;
import com.platform.aix.common.datacommon.db.service.PuzzleHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月08日 13:21
 */
@Service
public class PuzzleHistoryServiceImpl extends AsyncServiceImpl<Integer, PuzzleHistory> implements PuzzleHistoryService {
    private Map<Integer, Map<Integer, PuzzleHistory>> puzzleHistoryIdMap = Maps.newConcurrentMap();
    private Map<Integer, PuzzleHistory> puzzleHistoryMap = new ConcurrentHashMap<>();
    @Autowired
    private PuzzleHistoryMapper puzzleHistoryMapper;

    @Override
    public IMybatisMapper<PuzzleHistory, Integer, ?> getMapper() {
        return puzzleHistoryMapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PuzzleHistory puzzleHistory) {

    }

    @Override
    protected void save(PuzzleHistory puzzleHistory) {

    }

    @Override
    protected void delete(Integer PK) {

    }

    @Override
    public PuzzleHistory queryById(Integer id) {
        return null;
    }

    @Override
    public void savePuzzleHistory(PuzzleHistory puzzleHistory) {
        puzzleHistoryMapper.insert(puzzleHistory);
    }

    @Override
    public PuzzleHistory savePuzzleHistoryReturnEntity(PuzzleHistory puzzleHistory) {
        PuzzleHistory puzzleHistory1 = puzzleHistoryMapper.insertReturnEntity(puzzleHistory);
        return puzzleHistory1;
    }

    @Override
    public void savePuzzleHistory(List<PuzzleHistory> puzzleHistoryList) {

    }

    @Override
    public List<PuzzleHistory> queryAll() {
        return puzzleHistoryMapper.selectByExample(null);
    }

    @Override
    public PuzzleHistory getOnePuzzleHistory(PuzzleHistory puzzleHistory) {
        return null;
    }

    @Override
    public int deleteAll() {
        return puzzleHistoryMapper.deleteByExample(null);
    }

    @Override
    public int deleteHistory(Integer id) {
        return puzzleHistoryMapper.deleteByPrimaryKey(id);
    }
}
