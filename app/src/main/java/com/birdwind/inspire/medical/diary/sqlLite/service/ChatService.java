package com.birdwind.inspire.medical.diary.sqlLite.service;

import android.content.Context;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.AbstractService;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.sqlLite.DatabaseConfig;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ChatDao;

import java.util.List;

public class ChatService extends AbstractService<ChatResponse.Response, ChatDao> {

    public ChatService(Context context) {
        super(context);
    }

    @Override
    protected ChatDao init(Context context) {
        return DatabaseConfig.getInstance(context).chatDao();
    }

    public List<ChatResponse.Response> getChatListByPID(long pid) {
        return dao.findChatByPID(pid);
    }

    public ChatResponse.Response getChatByID(long id) {
        return dao.findOne(id);
    }
}
