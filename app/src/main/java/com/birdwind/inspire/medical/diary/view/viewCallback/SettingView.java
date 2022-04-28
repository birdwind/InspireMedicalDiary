package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;

public interface SettingView extends BaseCustomView {
    void onUpdateAvatar(boolean isSuccess, UploadMediaResponse.Response uploadMediaResponse);
}
