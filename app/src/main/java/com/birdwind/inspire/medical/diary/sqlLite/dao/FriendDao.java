package com.birdwind.inspire.medical.diary.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.BaseDao;
import com.birdwind.inspire.medical.diary.model.response.FriendResponse;

@Dao
public interface FriendDao extends BaseDao<FriendResponse.Response> {

    @Override
    default String getTableName() {
        return "friend";
    }

    @Override
    @Query(value = "SELECT * FROM friend F WHERE F.pid = :pid")
    FriendResponse.Response findOne(long pid);
}
