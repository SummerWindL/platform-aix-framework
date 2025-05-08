package com.platform.aix.common.datacommon.db.service.pikai.impl;

import com.platform.aix.common.datacommon.cache.impl.AsyncServiceImpl;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.db.dao.PikaiUserConfMapper;
import com.platform.aix.common.datacommon.db.domain.PikaiUserConf;
import com.platform.aix.common.datacommon.db.domain.PikaiUserConfExample;
import com.platform.aix.common.datacommon.db.service.pikai.PikaiUserConfService;
import com.platform.aix.controller.pikai.common.request.PikaiUserConfReq;
import com.platform.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月08日 17:07
 */
@Slf4j
@Service
public class PikaiUserConfServiceImpl extends AsyncServiceImpl<Integer, PikaiUserConf> implements PikaiUserConfService {

    @Autowired
    private PikaiUserConfMapper pikaiUserConfMapper;
    @Override
    public IMybatisMapper<PikaiUserConf, Integer, ?> getMapper() {
        return pikaiUserConfMapper;
    }

    @Override
    public void clearCache() {

    }



    @Override
    protected void save(PikaiUserConf pikaiUserConf) {

    }

    @Override
    protected void delete(Integer PK) {

    }


    @Override
    public int doSave(PikaiUserConfReq conf) {
        PikaiUserConf record = new PikaiUserConf();
        BeanUtil.copyPropertiesIgnoreNull(record,conf);
        return pikaiUserConfMapper.updateOrInsertSelective(record);
    }

    @Override
    public void update(PikaiUserConf conf) {

    }


    @Override
    public PikaiUserConf getPikaiUserConf(String userId, String confItemCode) {
        return pikaiUserConfMapper.selectUserConf(userId,confItemCode);
    }
}
