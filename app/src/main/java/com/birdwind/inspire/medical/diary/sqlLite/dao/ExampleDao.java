package com.birdwind.inspire.medical.diary.sqlLite.dao;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.BaseDao;
import com.birdwind.inspire.medical.diary.model.response.ExampleResponse;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ExampleDao extends BaseDao<ExampleResponse.Response> {

    @Override
    default String getTableName() {
        return "example";
    }

    @Override
    @Query(value = "SELECT * FROM example E WHERE E.SMSToken = :smsToken")
    ExampleResponse.Response findOne(long smsToken);
}
