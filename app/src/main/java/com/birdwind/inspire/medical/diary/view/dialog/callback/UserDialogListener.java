package com.birdwind.inspire.medical.diary.view.dialog.callback;

import com.birdwind.inspire.medical.diary.base.basic.BaseDialogListener;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public interface UserDialogListener extends BaseDialogListener {
    void userDialogAdded(BaseResponse response);

    void userDialogClose();
}
