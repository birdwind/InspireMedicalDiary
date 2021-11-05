package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.CheckDiseaseResponse;

public interface QRCodeView extends BaseCustomView {

    void checkDisease(boolean isSuccess, CheckDiseaseResponse.Response response);
}
