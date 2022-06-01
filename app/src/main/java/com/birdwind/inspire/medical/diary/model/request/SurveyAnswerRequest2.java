package com.birdwind.inspire.medical.diary.model.request;

import com.birdwind.inspire.medical.diary.base.network.request.AbstractRequest;

import java.util.ArrayList;
import java.util.List;

public class SurveyAnswerRequest2 extends AbstractRequest {
    private int SurveyID;

    private List<QuestionAnswerRequest> Questions;

    public SurveyAnswerRequest2(int surveyID) {
        SurveyID = surveyID;
        Questions = new ArrayList<>();
    }

    public int getSurveyID() {
        return SurveyID;
    }

    public void setSurveyID(int surveyID) {
        SurveyID = surveyID;
    }

    public List<QuestionAnswerRequest> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<QuestionAnswerRequest> questions) {
        Questions = questions;
    }

    public static class QuestionAnswerRequest extends AbstractRequest {
        private int QuestionID;

        private String Value;

        public QuestionAnswerRequest(int questionID, String value) {
            QuestionID = questionID;
            Value = value;
        }

        public int getQuestionID() {
            return QuestionID;
        }

        public void setQuestionID(int questionID) {
            QuestionID = questionID;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}
