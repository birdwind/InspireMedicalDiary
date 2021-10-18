package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.ScoreDetailResponse;

public interface ScoreDetailDialogView extends BaseCustomView {
    void onGetDetailSuccess(ScoreDetailResponse.Response response);
}
