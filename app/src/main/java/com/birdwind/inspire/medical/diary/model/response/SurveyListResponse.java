package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

import java.util.Date;
import java.util.List;

public class SurveyListResponse extends AbstractListResponse<SurveyListResponse.Response> {
    public class Response implements BaseResponse {

        private int SurveyID;

        private int UID;

        private int ConditionID;

        private int RespondentType;

        private int SurveySchedule;

        private String SurveyName;

        private String SurveyDescription;

        private int UserC;

        private Date TimeC;

        private int UserU;

        private Date TimeU;

        private boolean IsEnable;

        private String NameC;

        private int QuestionsCount;

        private int PrescribedPatientCount;

        private List<String> Questions; // TODO:需要重設Questions物件

        private String ConditionName;

        private String RespondentName;

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

        public int getConditionID() {
            return ConditionID;
        }

        public void setConditionID(int conditionID) {
            ConditionID = conditionID;
        }

        public int getRespondentType() {
            return RespondentType;
        }

        public void setRespondentType(int respondentType) {
            RespondentType = respondentType;
        }

        public int getSurveySchedule() {
            return SurveySchedule;
        }

        public void setSurveySchedule(int surveySchedule) {
            SurveySchedule = surveySchedule;
        }

        public String getSurveyName() {
            return SurveyName;
        }

        public void setSurveyName(String surveyName) {
            SurveyName = surveyName;
        }

        public String getSurveyDescription() {
            return SurveyDescription;
        }

        public void setSurveyDescription(String surveyDescription) {
            SurveyDescription = surveyDescription;
        }

        public int getUserC() {
            return UserC;
        }

        public void setUserC(int userC) {
            UserC = userC;
        }

        public Date getTimeC() {
            return TimeC;
        }

        public void setTimeC(Date timeC) {
            TimeC = timeC;
        }

        public int getUserU() {
            return UserU;
        }

        public void setUserU(int userU) {
            UserU = userU;
        }

        public Date getTimeU() {
            return TimeU;
        }

        public void setTimeU(Date timeU) {
            TimeU = timeU;
        }

        public boolean isEnable() {
            return IsEnable;
        }

        public void setEnable(boolean enable) {
            IsEnable = enable;
        }

        public String getNameC() {
            return NameC;
        }

        public void setNameC(String nameC) {
            NameC = nameC;
        }

        public int getQuestionsCount() {
            return QuestionsCount;
        }

        public void setQuestionsCount(int questionsCount) {
            QuestionsCount = questionsCount;
        }

        public int getPrescribedPatientCount() {
            return PrescribedPatientCount;
        }

        public void setPrescribedPatientCount(int prescribedPatientCount) {
            PrescribedPatientCount = prescribedPatientCount;
        }

        public List<String> getQuestions() {
            return Questions;
        }

        public void setQuestions(List<String> questions) {
            Questions = questions;
        }

        public String getConditionName() {
            return ConditionName;
        }

        public void setConditionName(String conditionName) {
            ConditionName = conditionName;
        }

        public String getRespondentName() {
            return RespondentName;
        }

        public void setRespondentName(String respondentName) {
            RespondentName = respondentName;
        }
    }
}
