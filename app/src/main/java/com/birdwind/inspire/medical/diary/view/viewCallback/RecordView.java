package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;

public interface RecordView extends BaseCustomView {
    void onGetVoiceQuiz(boolean isSuccess, VoiceQuizResponse.Response response);
}
