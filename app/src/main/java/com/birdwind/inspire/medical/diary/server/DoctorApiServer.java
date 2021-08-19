package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum DoctorApiServer implements BaseEnums {
    GET_MY_PATIENT("getmypatient"), GET_PATIENT_MEMBER("getpatientmember"), GET_PATIENT_MESSAGES(
        "getpatientmessages"), CHECK_QRCODE_UID("checkqrcodeuid"), ADD_PATIENT("addpatient");

    DoctorApiServer(String url) {
        this.url = "doctor/" + url;
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
