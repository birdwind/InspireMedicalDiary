package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum DoctorApiServer implements BaseEnums {
    GET_MY_PATIENT("getmypatient"),
    GET_PATIENT_MEMBER("getpatientmember"),
    SEND_MESSAGE_TO_PATIENT("sendmessagetopatient"),
    SEND_SEE_ME_TO_PATIENT("sendseemetopatient"),
    GET_PATIENT_MESSAGES("getpatientmessages"),
    GET_FAMILY_TEST_REPORT("GetFamilyTestReport"),
    GET_PATIENT_TEST_REPORT("getpatienttestreport"),
    GET_PATIENT_VOICE_TEST_LIST("GetPatientVoiceTestList"),
    GET_TEST_DETAIL("gettestdetail"),
    SEND_PRIVATE_MESSAGE("sendprivatemessage"),
    GET_PRIVATE_MESSAGE("getprivatemessage"),
    CHECK_QRCODE_UID("checkqrcodeuid"),
    ADD_PATIENT("addpatient");

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
