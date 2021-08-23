package com.birdwind.inspire.medical.diary.sqlLite.service;

import android.content.Context;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.AbstractService;
import com.birdwind.inspire.medical.diary.model.response.FriendResponse;
import com.birdwind.inspire.medical.diary.sqlLite.DatabaseConfig;
import com.birdwind.inspire.medical.diary.sqlLite.dao.FriendDao;

import java.util.List;

public class FriendService extends AbstractService<FriendResponse.Response, FriendDao> {

    public FriendService(Context context) {
        super(context);
    }

    @Override
    protected FriendDao init(Context context) {
        return DatabaseConfig.getInstance(context).friendDao();
    }

    public List<FriendResponse.Response> getFriends() {
        return dao.findAll();
    }
}
