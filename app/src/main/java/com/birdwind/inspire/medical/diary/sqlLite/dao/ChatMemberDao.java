package com.birdwind.inspire.medical.diary.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.BaseDao;
import com.birdwind.inspire.medical.diary.model.ChatMemberModel;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;

import java.util.List;

@Dao
public interface ChatMemberDao extends BaseDao<ChatMemberModel> {

    @Override
    default String getTableName() {
        return "chat_member";
    }

    @Override
    @Query(value = "SELECT * FROM chat_member CM WHERE CM.pid = :pid")
    ChatMemberModel findOne(long pid);

    @Query(value = "SELECT * FROM chat_member CM WHERE CM.pid = :pid")
    List<ChatMemberModel> findChatMemberByPID(long pid);
}
