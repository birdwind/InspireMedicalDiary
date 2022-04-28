package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;

import java.util.List;

public interface PatientManagerView extends BaseCustomView {

    void onGetSurvey(boolean isSuccess, List<SurveyListResponse.Response> response, int identity);

    void onSetSurvey(boolean isSuccess, String response, int identity);

    void onGetInformation(boolean isSuccess, InformationResponse.Response informationResponse);
}
