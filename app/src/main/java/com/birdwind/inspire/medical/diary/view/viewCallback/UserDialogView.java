package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.AddUserResponse;

public interface UserDialogView extends BaseCustomView {
    void onAddUser(boolean isSuccess, AddUserResponse.Response response);
}
