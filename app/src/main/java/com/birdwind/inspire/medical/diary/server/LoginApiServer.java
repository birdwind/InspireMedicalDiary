package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum LoginApiServer implements BaseEnums {
    LOGIN("login"),
    SEND_PHONE_VERIFICATION("sendphoneverification"),
    CHECK_PHONE_VERIFY("checkphoneverify"),
    UPDATE_FCM_TOKEN("UpdateFCMToken");

    LoginApiServer(String url) {
        this.url = "/Login/" + url;
    }

    private String url;

    @Override
    public Serializable valueOf() {
        return url;
    }

    @Override
    public String valueOfName() {
        return url;
    }
}
