package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.model.response.UploadRecordResponse;
import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordView;

import java.io.File;

import okhttp3.MultipartBody;

public class RecordPresenter extends AbstractPresenter<RecordView> {
    public RecordPresenter(RecordView baseView) {
        super(baseView);
    }

    public void getVoiceQuiz() {
        initMap();

        addDisposable(
            apiServer.executePostFormUrlEncode(PatientApiServer.GET_VOICE_TEST.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<VoiceQuizResponse>(this, baseView, "GetVoiceQuiz", null, VoiceQuizResponse.class,
                true) {
                @Override
                public void onSuccess(VoiceQuizResponse response) {
                    baseView.onGetVoiceQuiz(true, response.getJsonData());
                }
            });
    }

    public void uploadRecord(File file, int id, ProgressRequestBody.UploadCallbacks uploadCallbacks) {
        initMap();
        paramsMap.put("VTID", id);
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, "audio/m4a", uploadCallbacks);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);
        addDisposable(
            apiServer.uploadFile(PatientApiServer.UPLOAD_VOICE_FILE.valueOfName(), paramsMap, headerMap, filePart),
            new AbstractObserver<UploadRecordResponse>(this, baseView, "UploadRecord", null, UploadRecordResponse.class,
                true) {
                @Override
                public void onSuccess(UploadRecordResponse response) {
                    baseView.onUploadRecord(true);
                }
            });
    }

}
