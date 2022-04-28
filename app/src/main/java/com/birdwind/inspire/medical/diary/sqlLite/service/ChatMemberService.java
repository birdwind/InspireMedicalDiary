package com.birdwind.inspire.medical.diary.sqlLite.service;

import android.content.Context;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.AbstractService;
import com.birdwind.inspire.medical.diary.model.ChatMemberModel;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.sqlLite.DatabaseConfig;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ChatDao;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ChatMemberDao;

import java.util.List;

public class ChatMemberService extends AbstractService<ChatMemberModel, ChatMemberDao> {

    public ChatMemberService(Context context) {
        super(context);
    }

    @Override
    protected ChatMemberDao init(Context context) {
        return DatabaseConfig.getInstance(context).chatMemberDao();
    }

    public List<ChatMemberModel> getChatMemberByPID(long pid) {
        return dao.findChatMemberByPID(pid);
    }
}
