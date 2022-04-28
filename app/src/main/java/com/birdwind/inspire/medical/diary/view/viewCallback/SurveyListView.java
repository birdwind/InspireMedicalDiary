package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.SurveyQuestionnaireListResponse;

import java.util.List;

public interface SurveyListView extends BaseCustomView {
    void getSurveyQuestionnaire(List<SurveyQuestionnaireListResponse.Response> surveyQuestionnaireListResponse);
}
