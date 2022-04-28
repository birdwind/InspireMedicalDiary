package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

import java.util.Date;

public class SurveyQuestionnaireListResponse extends AbstractListResponse<SurveyQuestionnaireListResponse.Response> {

    public static class Response implements BaseResponse {
        private int QuestionnaireID;

        private Date SubmitTime;

        private int SurveyID;

        private int UID;

        private int DoctorID;

        private int PatientID;

        private int QuestionsCount;

        private String Responses;

        private String SurveyName;

        public int getQuestionnaireID() {
            return QuestionnaireID;
        }

        public void setQuestionnaireID(int questionnaireID) {
            QuestionnaireID = questionnaireID;
        }

        public Date getSubmitTime() {
            return SubmitTime;
        }

        public void setSubmitTime(Date submitTime) {
            SubmitTime = submitTime;
        }

        public int getSurveyID() {
            return SurveyID;
        }

        public void setSurveyID(int surveyID) {
            SurveyID = surveyID;
        }

        public int getUID() {
            return UID;
        }

        public void setUID(int UID) {
            this.UID = UID;
        }

        public int getDoctorID() {
            return DoctorID;
        }

        public void setDoctorID(int doctorID) {
            DoctorID = doctorID;
        }

        public int getPatientID() {
            return PatientID;
        }

        public void setPatientID(int patientID) {
            PatientID = patientID;
        }

        public int getQuestionsCount() {
            return QuestionsCount;
        }

        public void setQuestionsCount(int questionsCount) {
            QuestionsCount = questionsCount;
        }

        public String getResponses() {
            return Responses;
        }

        public void setResponses(String responses) {
            Responses = responses;
        }

        public String getSurveyName() {
            return SurveyName;
        }

        public void setSurveyName(String surveyName) {
            SurveyName = surveyName;
        }
    }
}
