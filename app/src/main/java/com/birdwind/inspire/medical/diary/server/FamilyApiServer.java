package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum FamilyApiServer implements BaseEnums {
    SEND_MESSAGE("sendmessage"), GET_MY_FAMILY_MESSAGES("getmyfamilymessages"), SEND_MESSAGE_TO_DOCTOR(
        "sendmessageyodoctor"), GET_MY_PRIVATE_MESSAGES("getmyprivatemessages"), CHECK_QRCODE_UID(
            "checkqrcodeuid"), BE_FAMILY("befamily"), GET_TEST_REPORT(
                "GetTestReport"), GET_TEST_DETAIL("GetTestDetail"), SUBMIT_PARKINSON_TEST(
                    "SubmitParkinsonTest"), SUBMIT_DEMENTIA_TEST("SubmitDementiaTest"), SUBMIT_PARKINSON_CAREGIVER_TEST(
                        "SubmitParkinsonCaregiverTest"), SUBMIT_DEMENTIA_CAREGIVER_TEST("SubmitDementiaCaregiverTest");

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
