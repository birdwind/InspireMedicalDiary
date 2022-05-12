package com.birdwind.inspire.medical.diary.view.viewCallback;

public interface CameraView extends BaseCustomView {
    void onUpload(boolean isSuccess, String url, boolean isVideo);
}
