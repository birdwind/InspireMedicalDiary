package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.RecordOrderResponse;

import java.util.List;

public interface RecorderOrderView extends BaseCustomView {
    void onGetPatientVoiceList(boolean isSuccess, List<RecordOrderResponse.Response> response);
}
