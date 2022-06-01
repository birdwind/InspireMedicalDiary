package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum MyApiServer implements BaseEnums {
    CHANGE_GROPE_PHOTO("ChangeGroupPhoto"),
    CHANGE_PHOTO("ChangePhoto"),
    GET_INFORMATION("GetInformation"),
    CHANGE_INFORMATION("ChangeInformation");

    MyApiServer(String url) {
        this.url = "/My/" + url;
    }

    private String url;

    @Override
    public Serializable valueOf() {
        return this.url;
    }

    @Override
    public String valueOfName() {
        return this.url;
    }
}
