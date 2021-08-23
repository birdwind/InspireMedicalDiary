package com.birdwind.inspire.medical.diary.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.BaseDao;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;

import java.util.List;

@Dao
public interface ChatDao extends BaseDao<ChatResponse.Response> {

    @Override
    default String getTableName() {
        return "chat";
    }

    @Override
    @Query(value = "SELECT * FROM chat C WHERE C.id = :id")
    ChatResponse.Response findOne(long id);

    @Query(value = "SELECT * FROM chat C WHERE C.pid = :pid")
    List<ChatResponse.Response> findChatByPID(long pid);
}
