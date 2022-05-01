package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;
import com.birdwind.inspire.medical.diary.server.SurveyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.DrawingView;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordVoiceDialogView;

import java.io.File;

import okhttp3.MultipartBody;

public class DrawingPresenter extends AbstractPresenter<DrawingView> {
    public DrawingPresenter(DrawingView baseView) {
        super(baseView);
    }

    public void getVoiceQuiz() {
//        initMap();
//
//        addDisposable(
//            apiServer.executePostFormUrlEncode(PatientApiServer.GET_VOICE_TEST.valueOfName(), paramsMap, fieldMap,
//                headerMap),
//            new AbstractObserver<VoiceQuizResponse>(this, baseView, "GetVoiceQuiz", null, VoiceQuizResponse.class,
//                true) {
//                @Override
//                public void onSuccess(VoiceQuizResponse response) {
//                    baseView.onGetVoiceQuiz(true, response.getJsonData());
//                }
//            });
    }

    public void uploadRecord(File file, ProgressRequestBody.UploadCallbacks uploadCallbacks) {
        initMap();
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, "audio/m4a", uploadCallbacks);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);
        addDisposable(
            apiServer.uploadFile(SurveyApiServer.UPLOAD_MEDIA_FILE.valueOfName(), paramsMap, headerMap, filePart),
            new AbstractObserver<UploadMediaResponse>(this, baseView, "UploadRecord", null, UploadMediaResponse.class,
                false) {
                @Override
                public void onSuccess(UploadMediaResponse response) {
                    baseView.onUpload(true, response.getJsonData().getMediaLink());
                }
            });
    }

}
