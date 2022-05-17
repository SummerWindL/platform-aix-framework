package com.platform.aix.common.datacommon.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CacheService<PK extends Serializable, T> {

    <S extends T> void updateCache(S entity);

    <S extends T> void updateCache(Iterable<S> entities);

    void deleteCache(PK pk);

    List<PK> deleteCache(Iterable<T> entities);

    T findById(PK pk);

    List<T> findAll();

    Map<PK, T> getMap();
}
