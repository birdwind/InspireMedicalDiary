package com.birdwind.inspire.medical.diary.presenter;

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

        RequestBody requestBody = packageToRequestBody(answer);

        addDisposable(
            apiServer.executePost(PatientApiServer.SUBMIT_HEADACHE_TEST.valueOfName(), paramsMap, requestBody,
                headerMap),
            new AbstractObserver<QuizResponse>(this, baseView, "SubmitQuiz", null, QuizResponse.class, true) {
                @Override
                public void onSuccess(QuizResponse response) {
                    baseView.submitSuccess(response.isSuccess());
                }
            });
    }
}
