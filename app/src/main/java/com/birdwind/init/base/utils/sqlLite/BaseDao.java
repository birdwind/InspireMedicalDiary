package com.birdwind.init.base.utils.sqlLite;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

public interface BaseDao<T> {

    /**
     * 儲存單項
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(T entity);

    /**
     * 儲存多項
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] save(List<T> entities);

    /**
     * 根據Entity主鍵刪除
     */
    @Delete
    void delete(T entity);

    @RawQuery
    int delete(SupportSQLiteQuery query);

    /**
     * 尋找資料表全部資料
     */
    @RawQuery
    List<T> findAll(SupportSQLiteQuery query);

    default List<T> findAll() {
        return findAll(new SimpleSQLiteQuery("SELECT * FROM " + getTableName()));
    }

    default void delete() {
        delete(new SimpleSQLiteQuery("DELETE FROM " + getTableName()));
    }

    T findOne(long id);

    String getTableName();

}
