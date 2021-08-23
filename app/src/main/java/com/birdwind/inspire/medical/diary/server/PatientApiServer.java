package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum PatientApiServer implements BaseEnums {
    SEND_MESSAGE("sendmessage"), GET_MY_MESSAGES("getmymessages"), GET_PATIENT_MEMBER(
        "getpatientmember"), CHECK_QRCODE_UID("checkqrcodeuid"), JOIN_MY_GROUP("joinmygroup");

    PatientApiServer(String url) {
        this.url = "patient/" + url;
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
