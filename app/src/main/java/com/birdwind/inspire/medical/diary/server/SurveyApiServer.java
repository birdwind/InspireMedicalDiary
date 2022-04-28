package com.birdwind.inspire.medical.diary.server;

import com.birdwind.inspire.medical.diary.base.enums.BaseEnums;

import java.io.Serializable;

public enum SurveyApiServer implements BaseEnums {
    GET_SURVEY_REPORT("GetSurveyReport"),
    GET_VOICE_LIST("GetVoiceList"),
    GET_SURVEY_DETAIL("GetSurveyDetail"),
    UPLOAD_VOICE_FILE("UploadVoiceFile"),
    GET_SURVEY("GetSurvey"),
    SET_SURVEY_FOR_USER("SetSurveyForUser"),
    SURVEY_RESPONSE("SurveyResponse"),
    SURVEY_REQUEST("SurveyRequest"),
    UPLOAD_MEDIA_FILE("UploadMediaFile"),
    QUESTIONNAIRE_LIST("QuestionnaireList"),
    QUESTIONNAIRE("Questionnaire");

    SurveyApiServer(String url) {
        this.url = "/Survey/" + url;
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
