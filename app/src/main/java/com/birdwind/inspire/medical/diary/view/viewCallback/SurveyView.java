package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.QuestionnaireResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyResponse;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;

public interface SurveyView extends BaseCustomView {
    void onGetSurvey(boolean isSuccess, SurveyResponse.Response response);

    void submitSuccess(boolean isSuccess);

    void onGetQuestionnaire(QuestionnaireResponse.Response questionnaireResponse);
    void onUploadRecord(UploadMediaResponse response);
}
