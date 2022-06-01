package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;

public interface SettingView extends BaseCustomView {
    void onGetInformation(boolean isSuccess, InformationResponse.Response response);
    void onUpdateAvatar(boolean isSuccess, UploadMediaResponse.Response uploadMediaResponse,
        IdentityEnums identityEnums);
}
