package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum PatientApiServer implements BaseEnums {
    CHECK_DISEASE("CheckDisease"),
    GET_MY_PATIENT("GetMyPatient"),
    GET_MEMBER("GetMember"),
    CHECK_QRCODE_UID("CheckQRCodeUid"),
    ADD_PATIENT("AddPatient"),
    BE_FAMILY("BeFamily"),
    CHANGE_PATIENT_NAME("ChangePatientName");

    PatientApiServer(String url) {
        this.url = "/Patient/" + url;
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
