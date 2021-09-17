package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import java.util.List;
import okhttp3.RequestBody;

public class QuizContentPresenter extends AbstractPresenter<QuizContentView> {
    public QuizContentPresenter(QuizContentView baseView) {
        super(baseView);
    }

    public void submit(List<Boolean> answer) {
        initMap();

        RequestBody requestBody = packageToRequestBody(answer);

        addDisposable(
            apiServer.executePost(PatientApiServer.SUBMIT_HEADACHE_TEST.valueOfName(), paramsMap, requestBody,
                headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "SubmitQuiz", null, AbstractResponse.class, true) {
                @Override
                public void onSuccess(AbstractResponse response) {

                }
            });
    }

}
