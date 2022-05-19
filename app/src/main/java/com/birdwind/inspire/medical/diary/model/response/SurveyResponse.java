package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.model.QuestionModel;

import java.util.List;

public class SurveyResponse extends AbstractResponse<SurveyResponse.Response> {

    public class Response implements BaseResponse {
        private int SurveyID;

        private float Version;

        private String SurveyName;

        private int QuestionsCount;

        private List<QuestionModel> Questions;

        public int getSurveyID() {
            return SurveyID;
        }

        public void setSurveyID(int surveyID) {
            SurveyID = surveyID;
        }

        public String getSurveyName() {
            return SurveyName;
        }

        public void setSurveyName(String surveyName) {
            SurveyName = surveyName;
        }

        public int getQuestionsCount() {
            return QuestionsCount;
        }

        public void setQuestionsCount(int questionsCount) {
            QuestionsCount = questionsCount;
        }

        public List<QuestionModel> getQuestions() {
            return Questions;
        }

        public void setQuestions(List<QuestionModel> questions) {
            Questions = questions;
        }

        public float getVersion() {
            return Version;
        }

        public void setVersion(float version) {
            Version = version;
        }
    }
}
