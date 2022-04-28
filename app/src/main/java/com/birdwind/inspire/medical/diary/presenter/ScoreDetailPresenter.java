package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.view.viewCallback.ScoreDetailDialogView;

public class ScoreDetailPresenter extends AbstractPresenter<ScoreDetailDialogView> {
    public ScoreDetailPresenter(ScoreDetailDialogView baseView) {
        super(baseView);
    }

    public void GetScoreDetail(int quizId) {
        initMap();

        // paramsMap.put("TID", quizId);
        //
        // addDisposable(
        // apiServer.executeGet(DoctorApiServer.GET_TEST_DETAIL.valueOfName(), paramsMap,
        // headerMap),
        // new AbstractObserver<ScoreDetailResponse>(this, baseView, "GetScoreDetail", null, ScoreDetailResponse.class,
        // true) {
        // @Override
        // public void onSuccess(ScoreDetailResponse response) {
        // baseView.onGetDetailSuccess(response.getJsonData());
        // }
        // });
    }
}
