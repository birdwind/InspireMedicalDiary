package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.view.viewCallback.SurveyView;

import java.util.List;

public class QuizContentPresenter extends AbstractPresenter<SurveyView> {
    public QuizContentPresenter(SurveyView baseView) {
        super(baseView);
    }

    public void submit(List<Integer> answer, boolean isFamily) {

        initMap();

        // String api = "";
        //
        // if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
        // if (isFamily) {
        // switch (App.userModel.getDiseaseEnums()) {
        // case HEADACHE:
        // baseView.submitSuccess(false, "您代理人的身分無法送出頭痛的問卷");
        // break;
        // case ALZHEIMER:
        // api = FamilyApiServer.SUBMIT_DEMENTIA_CAREGIVER_TEST.valueOfName();
        // break;
        // case PERKINS:
        // api = FamilyApiServer.SUBMIT_PARKINSON_CAREGIVER_TEST.valueOfName();
        // break;
        // }
        // } else {
        // switch (App.userModel.getDiseaseEnums()) {
        // case HEADACHE:
        // baseView.submitSuccess(false, "您代理人的身分無法送出頭痛的問卷");
        // break;
        // case ALZHEIMER:
        // api = PatientApiServer.SUBMIT_DEMENTIA_TEST.valueOfName();
        // break;
        // case PERKINS:
        // api = PatientApiServer.SUBMIT_PARKINSON_TEST.valueOfName();
        // break;
        // }
        // }
        // } else {
        // switch (App.userModel.getDiseaseEnums()) {
        // case HEADACHE:
        // api = PatientApiServer.SUBMIT_HEADACHE_TEST.valueOfName();
        // break;
        // case ALZHEIMER:
        // api = PatientApiServer.SUBMIT_DEMENTIA_TEST.valueOfName();
        // break;
        // case PERKINS:
        // api = PatientApiServer.SUBMIT_PARKINSON_TEST.valueOfName();
        // break;
        // }
        // }
        //
        // RequestBody requestBody = packageToRequestBody(answer);
        //
        // addDisposable(apiServer.executePost(api, paramsMap, requestBody, headerMap),
        // new AbstractObserver<QuizResponse>(this, baseView, "SubmitQuiz", null, QuizResponse.class, true) {
        // @Override
        // public void onSuccess(QuizResponse response) {
        // baseView.submitSuccess(response.isSuccess(), null);
        // }
        // });
    }
}
