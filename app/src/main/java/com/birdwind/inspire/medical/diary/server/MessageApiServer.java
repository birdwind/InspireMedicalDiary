package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum MessageApiServer implements BaseEnums {
    SEND_MESSAGE("SendMessage"),
    GET_MESSAGE("GetMessages"),
    SEND_PRIVATE_MESSAGE("SendPrivateMessage"),
    GET_PRIVATE_MESSAGE("GetPrivateMessage"),
    SEND_SEE_ME_TO_PATIENT("SendSeeMeToPatient");

    MessageApiServer(String url) {
        this.url = "/Message/" + url;
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
