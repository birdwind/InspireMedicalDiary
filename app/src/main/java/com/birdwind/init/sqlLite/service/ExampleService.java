package com.birdwind.init.sqlLite.service;

import com.birdwind.init.base.utils.sqlLite.AbstractService;
import com.birdwind.init.model.response.ExampleResponse;
import com.birdwind.init.sqlLite.DatabaseConfig;
import com.birdwind.init.sqlLite.dao.ExampleDao;

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
