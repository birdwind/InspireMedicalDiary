package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.birdwind.inspire.medical.diary.model.request.SurveyAnswerRequest;

public class QuestionnaireResponse extends AbstractResponse<QuestionnaireResponse.Response> {
    public static class Response implements BaseResponse {
        private SurveyResponse.Response SurveyResponse;

        private SurveyAnswerRequest SurveyRequest;

        public SurveyResponse.Response getSurveyResponse() {
            return SurveyResponse;
        }

        public void setSurveyResponse(SurveyResponse.Response surveyResponse) {
            SurveyResponse = surveyResponse;
        }

        public SurveyAnswerRequest getSurveyRequest() {
            return SurveyRequest;
        }

        public void setSurveyRequest(SurveyAnswerRequest surveyRequest) {
            SurveyRequest = surveyRequest;
        }
    }
}
