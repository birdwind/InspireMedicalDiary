package com.birdwind.inspire.medical.diary.model.request;

import com.birdwind.inspire.medical.diary.base.network.request.AbstractRequest;
import com.birdwind.inspire.medical.diary.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class SurveyAnswerRequest extends AbstractRequest {
    private int SurveyID;

    private List<QuestionAnswerRequest> Questions;

    public SurveyAnswerRequest(int surveyID, List<QuestionModel> questionModels) {
        SurveyID = surveyID;
        Questions = new ArrayList<>();
        for (QuestionModel questionModel : questionModels) {
            Questions.add(new QuestionAnswerRequest(questionModel.getQuestionID(), null));
        }
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

    public void updateAnswer(int questionId, String answer) {
        for (QuestionAnswerRequest questionAnswerRequest : Questions) {
            if (questionAnswerRequest.QuestionID == questionId) {
                questionAnswerRequest.setValue(answer);
                break;
            }
        }
    }

    public boolean isComplete() {
        for (QuestionAnswerRequest questionAnswerRequest : Questions) {
            if (questionAnswerRequest.getValue() == null) {
                return false;
            }
        }
        return true;
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
