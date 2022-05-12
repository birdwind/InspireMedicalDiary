package com.birdwind.inspire.medical.diary.model;

import java.io.Serializable;

public class SurveyWebSocketModel implements Serializable {
    private int QuestionnaireID;

    private Long PID;

    public int getQuestionnaireID() {
        return QuestionnaireID;
    }

    public void setQuestionnaireID(int questionnaireID) {
        QuestionnaireID = questionnaireID;
    }

    public Long getPID() {
        return PID;
    }

    public void setPID(Long PID) {
        this.PID = PID;
    }
}
