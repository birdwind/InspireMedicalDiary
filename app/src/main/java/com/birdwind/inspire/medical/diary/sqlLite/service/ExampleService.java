package com.birdwind.inspire.medical.diary.sqlLite.service;

import com.birdwind.inspire.medical.diary.base.utils.sqlLite.AbstractService;
import com.birdwind.inspire.medical.diary.model.response.ExampleResponse;
import com.birdwind.inspire.medical.diary.sqlLite.DatabaseConfig;
import com.birdwind.inspire.medical.diary.sqlLite.dao.ExampleDao;

import android.content.Context;

public class ExampleService
    extends AbstractService<ExampleResponse.Response, ExampleDao> {

    public ExampleService(Context context) {
        super(context);
    }

    @Override
    protected ExampleDao init(Context context) {
        return DatabaseConfig.getInstance(context).exampleDao();
    }

    public ExampleResponse.Response getSMSToken(String SMSToken) {
        return dao.findOne(0);
    }
}
