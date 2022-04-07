package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.AddUserResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;

import java.util.List;

public interface UserDialogView extends BaseCustomView {
    void onAddUser(boolean isSuccess, AddUserResponse.Response response);

    void onGetSurvey(boolean isSuccess, List<SurveyListResponse.Response> response);
    void onSetSurvey(boolean isSuccess, String response);
}
