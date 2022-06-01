package com.birdwind.inspire.medical.diary.presenter;

import android.net.Uri;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.FileUtils;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;
import com.birdwind.inspire.medical.diary.server.MyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.SettingView;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;

public class SettingPresenter extends AbstractPresenter<SettingView> {
    public SettingPresenter(SettingView baseView) {
        super(baseView);
    }

    public void uploadAvatar(File file, ProgressRequestBody.UploadCallbacks uploadCallbacks,
        IdentityEnums identityEnums) {
        initMap();
        String api = MyApiServer.CHANGE_PHOTO.valueOfName();
        if (identityEnums == IdentityEnums.PAINTER) {
            api = MyApiServer.CHANGE_GROPE_PHOTO.valueOfName();
        }
        ProgressRequestBody progressRequestBody =
            new ProgressRequestBody(file, FileUtils.getMimeType(Uri.fromFile(file)), uploadCallbacks);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);
        addDisposable(apiServer.uploadFile(api, paramsMap, headerMap, filePart),
            new AbstractObserver<UploadMediaResponse>(this, baseView, "UploadAvatar", null, UploadMediaResponse.class,
                false) {
                @Override
                public void onSuccess(UploadMediaResponse response) {
                    baseView.onUpdateAvatar(true, response.getJsonData(), identityEnums);
                }
            });
    }

    public void getBasicInfo(int uid, IdentityEnums identityEnums) {
        initMap();
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("UID", uid);
        paramsMap.put("RespondentType", identityEnums.getType() + 1);
        addDisposable(apiServer.executeGet(MyApiServer.GET_INFORMATION.valueOfName(), paramsMap, headerMap),
                new AbstractObserver<InformationResponse>(this, baseView, "GetInformation", null, InformationResponse.class,
                        true) {
                    @Override
                    public void onSuccess(InformationResponse response) {
                        baseView.onGetInformation(true, response.getJsonData());
                    }
                });
    }
}
