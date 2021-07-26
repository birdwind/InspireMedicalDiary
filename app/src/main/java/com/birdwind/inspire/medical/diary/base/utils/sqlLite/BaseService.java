package com.birdwind.inspire.medical.diary.base.utils.sqlLite;

import java.util.List;

public interface BaseService<E extends Object, T extends BaseDao<E>> {

    List<E> findAll();

    long save(E entity);

    long[] save(List<E> entities);

    void delete();

    void delete(E entity);
}
