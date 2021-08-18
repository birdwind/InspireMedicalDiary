package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.UserResponse;

public interface ScanView extends BaseCustomView {

    void onCheckUidResponse(boolean isSuccess, UserResponse userResponse);

}
