package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;

public interface RecordVoiceDialogView extends BaseCustomView {
    void onGetVoiceQuiz(boolean isSuccess, VoiceQuizResponse.Response response);

    void onUploadRecord(boolean isSuccess, String url);
}
