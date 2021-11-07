package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum PatientApiServer implements BaseEnums {
    CHECK_MY_DISEASE("checkmydisease"), SEND_MESSAGE("sendmessage"), GET_MY_MESSAGES("getmymessages"), GET_MY_MEMBER(
        "getmymember"), SUBMIT_HEADACHE_TEST("submitheadachetest"), SUBMIT_DEMENTIA_TEST(
            "submitdementiatest"), SUBMIT_PARKINSON_TEST("SubmitParkinsonTest"), GET_TEST_REPORT(
                "GetTestReport"), GET_TEST_DETAIL("GetTestDetail"), GET_VOICE_TEST(
                    "GetVoiceTest"), UPLOAD_VOICE_FILE("UploadVoiceFile"), GET_VOICE_TEST_LIST("GetVoiceTestList");

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
