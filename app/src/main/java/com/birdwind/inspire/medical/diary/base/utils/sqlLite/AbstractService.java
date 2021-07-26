package com.birdwind.inspire.medical.diary.base.utils.sqlLite;

import java.util.List;

import android.content.Context;

public abstract class AbstractService<E extends Object, T extends BaseDao<E>> implements BaseService<E, T> {
    protected Context context;

    protected T dao;

    public AbstractService(Context context) {
        this.context = context;
        dao = init(context);
    }

    protected abstract T init(Context context);

    protected T getRepository(Context context) {
        return init(context);
    }

    @Override
    public List<E> findAll() {
        return dao.findAll();
    }

    @Override
    public long save(E entity) {
        return dao.save(entity);
    }

    @Override
    public long[] save(List<E> entities) {
        return dao.save(entities);
    }

    @Override
    public void delete() {
        dao.delete();
    }

    @Override
    public void delete(E entity) {
        dao.delete(entity);
    }
}
