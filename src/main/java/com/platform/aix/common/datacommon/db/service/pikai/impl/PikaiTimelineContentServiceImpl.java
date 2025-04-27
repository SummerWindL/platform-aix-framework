package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiTimelineContentMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiTimelineContent;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiTimelineContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月17日 11:02
 */
@Service
public class PikaiTimelineContentServiceImpl extends AsyncServiceImpl<Integer, PikaiTimelineContent> implements PikaiTimelineContentService {

    @Autowired
    private PikaiTimelineContentMapper mapper;
    @Override
    public IMybatisMapper<PikaiTimelineContent, Integer, ?> getMapper() {
        return mapper;
    }

    @Override
    public void clearCache() {

    }

    @Override
    protected void update(PikaiTimelineContent pikaiTimelineContent) {

    }

    @Override
    protected void save(PikaiTimelineContent pikaiTimelineContent) {

    }

    @Override
    protected void delete(Integer PK) {

    }

    @Override
    public int savePikaiTimelineContent(PikaiTimelineContent content) {
        return mapper.insert(content);
    }

    @Override
    public int updatePikaiTimelineContent(PikaiTimelineContent content) {
        return mapper.updateByPrimaryKey(content);
    }

    @Override
    public int deletPikaiTimelineContent(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public PikaiTimelineContent queryPikaiTimelineContentOne(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
