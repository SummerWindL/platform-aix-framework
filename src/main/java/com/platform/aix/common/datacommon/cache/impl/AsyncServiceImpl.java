package com.platform.aix.common.datacommon.cache.impl;

import com.platform.aix.common.constants.DBSaveType;
import com.platform.aix.common.datacommon.async.AsyncPersistenceWorker;
import com.platform.aix.common.datacommon.cache.AsyncService;
import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.common.datacommon.db.dao.IMybatisMapper;
import com.platform.aix.common.datacommon.sync.SyncPersistenceWorker;
import com.platform.common.util.ConvertListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class AsyncServiceImpl<PK extends Serializable, T extends KeyMethodInterface<PK>> extends RedisCacheServiceImpl<PK, T> implements AsyncService<PK, T> {
    @Autowired
    private AsyncPersistenceWorker asyncPersistenceWorker;
    @Autowired
    private SyncPersistenceWorker syncPersistenceWorker;

    public abstract IMybatisMapper<T, PK, ?> getMapper();

    @Override
    public T asyncUpdate(T t) {
        asyncUpdate(t, DBSaveType.ASYNC.value);
        return t;
    }

    @Override
    public <S extends T> Iterable<S> asyncUpdate(Iterable<S> entities) {
        asyncUpdate(entities, DBSaveType.ASYNC.value);
        return entities;
    }

    @Override
    public void asyncDel(PK pk) {
        deleteCache(pk);
        asyncPersistenceWorker.async(() -> getMapper().deleteByPrimaryKey(pk));
    }

    @Override
    public void asyncDel(Iterable<T> entities) {
        List<PK> pkList = deleteCache(entities);
        int totalSize = pkList.size();
        //批量删除sql中in里最大数量，必须小于等于1000
        int batchSize = 900;
        int batchCount = totalSize / batchSize + (totalSize % batchSize == 0 ? 0 : 1);
        for (int i = 0; i < batchCount; i++) {
            List<PK> finalPkList = ConvertListUtils.convertList(i + 1, batchSize, pkList);
            asyncPersistenceWorker.async(() -> getMapper().deleteBatchByPrimaryKey(finalPkList));
        }
    }

    @Override
    public void asyncUpdate(T t, int saveType) {
        updateCache(t);
        if (DBSaveType.ASYNC.value == saveType) {
            if (dataServiceConfig.isAsyncDb()) {
                asyncPersistenceWorker.async(() -> getMapper().updateOrInsertSelective(t));
            } else {
                getMapper().updateOrInsertSelective(t);
            }
        } else if (DBSaveType.SYNC_BATCH.value == saveType) {
            syncPersistenceWorker.sync(() -> getMapper().updateOrInsertSelective(t));
        } else {
            getMapper().updateOrInsertSelective(t);
        }
    }

    @Override
    public <S extends T> void asyncUpdate(Iterable<S> entities, int saveType) {
        updateCache(entities);
        entities.forEach(s -> {
            if (DBSaveType.ASYNC.value == saveType && dataServiceConfig.isAsyncDb()) {
                asyncPersistenceWorker.async(() -> getMapper().updateOrInsertSelective(s));
            } else {
                syncPersistenceWorker.sync(() -> getMapper().updateOrInsertSelective(s));
            }
        });
    }

    @Override
    public void coverCache(boolean fromRedis) {
        clearCache();
        List<T> list = fromRedis ? getAllFromRedis() : getMapper().selectByExample(null);
        if (Objects.nonNull(list)) {
            updateCache(list);
        }
    }
}
