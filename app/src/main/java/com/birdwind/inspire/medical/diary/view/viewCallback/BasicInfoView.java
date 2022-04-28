package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.InformationResponse;

public interface BasicInfoView extends BaseCustomView {
    void onGetInformation(boolean isSuccess, InformationResponse.Response response);
    void onSetInformation(boolean isSuccess);
}
