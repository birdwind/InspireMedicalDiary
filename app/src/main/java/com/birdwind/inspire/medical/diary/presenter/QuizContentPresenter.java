package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.QuizResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;

import java.util.List;

import okhttp3.RequestBody;

public class QuizContentPresenter extends AbstractPresenter<QuizContentView> {
    public QuizContentPresenter(QuizContentView baseView) {
        super(baseView);
    }

    public void submit(List<Integer> answer) {
        initMap();

        String api = "";
        switch (App.userModel.getDiseaseEnums()) {
            case HEADACHE:
                api = PatientApiServer.SUBMIT_HEADACHE_TEST.valueOfName();
                break;
            case ALZHEIMER:
                api = PatientApiServer.SUBMIT_DEMENTIA_TEST.valueOfName();
                break;
            case PERKINS:
                api = PatientApiServer.SUBMIT_PARKINSON_TEST.valueOfName();
                break;
        }

        RequestBody requestBody = packageToRequestBody(answer);

        addDisposable(apiServer.executePost(api, paramsMap, requestBody, headerMap),
            new AbstractObserver<QuizResponse>(this, baseView, "SubmitQuiz", null, QuizResponse.class, true) {
                @Override
                public void onSuccess(QuizResponse response) {
                    baseView.submitSuccess(response.isSuccess());
                }
            });
    }
}
