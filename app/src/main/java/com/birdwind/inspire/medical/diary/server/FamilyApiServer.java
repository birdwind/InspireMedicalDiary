package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum FamilyApiServer implements BaseEnums {
    GET_MY_FAMILY_MESSAGES("getmyfamilymessages"), CHECK_QRCODE_UID("checkqrcodeuid"), BE_FAMILY("befamily");

    FamilyApiServer(

        String url) {
        this.url = "family/" + url;
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
