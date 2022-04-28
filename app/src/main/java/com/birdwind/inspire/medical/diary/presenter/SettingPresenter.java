package com.birdwind.inspire.medical.diary.presenter;

import android.net.Uri;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.FileUtils;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;
import com.birdwind.inspire.medical.diary.server.MyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.SettingView;

import java.io.File;

import okhttp3.MultipartBody;

public class SettingPresenter extends AbstractPresenter<SettingView> {
    public SettingPresenter(SettingView baseView) {
        super(baseView);
    }

    public void uploadAvatar(File file, ProgressRequestBody.UploadCallbacks uploadCallbacks) {
        initMap();
        ProgressRequestBody progressRequestBody =
            new ProgressRequestBody(file, FileUtils.getMimeType(Uri.fromFile(file)), uploadCallbacks);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);
        addDisposable(apiServer.uploadFile(MyApiServer.CHANGE_PHOTO.valueOfName(), paramsMap, headerMap, filePart),
            new AbstractObserver<UploadMediaResponse>(this, baseView, "UploadAvatar", null, UploadMediaResponse.class,
                false) {
                @Override
                public void onSuccess(UploadMediaResponse response) {
                    baseView.onUpdateAvatar(true, response.getJsonData());
                }
            });
    }
}
