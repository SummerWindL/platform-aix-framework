package com.platform.aix.common.datacommon.cache;

import java.util.List;
import java.util.Map;

public interface AsyncService<PK, T> {

    T asyncUpdate(T t);

    <S extends T> Iterable<S> asyncUpdate(Iterable<S> entities);

    void asyncDel(PK pk);

    void asyncDel(Iterable<T> entities);

    T findById(PK id);

    List<T> findAll();

    Map<PK, T> getMap();

    void asyncUpdate(T t, int saveType);

    <S extends T> void asyncUpdate(Iterable<S> entities, int saveType);
}
