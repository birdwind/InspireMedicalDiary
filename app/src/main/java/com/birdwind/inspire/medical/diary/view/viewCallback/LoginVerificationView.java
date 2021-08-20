package com.birdwind.inspire.medical.diary.view.viewCallback;

public interface LoginVerificationView extends BaseCustomView {

    void onSendVerify(boolean isSuccess);

    void onVerify(boolean isSuccess);
}
