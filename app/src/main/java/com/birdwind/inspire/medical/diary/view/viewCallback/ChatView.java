package com.birdwind.inspire.medical.diary.view.viewCallback;

public interface ChatView extends BaseCustomView {
    void onGetChatMessage(boolean isSuccess);

    void onGetChatMember(boolean isSuccess);

    void onSendMessage(boolean isSuccess);
}
