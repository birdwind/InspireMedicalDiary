package com.birdwind.inspire.medical.diary.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.BaseDao;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;

@Dao
public interface FriendDao extends BaseDao<PatientResponse.Response> {

    @Override
    default String getTableName() {
        return "friend";
    }

    @Override
    @Query(value = "SELECT * FROM friend F WHERE F.pid = :pid")
    PatientResponse.Response findOne(long pid);
}
