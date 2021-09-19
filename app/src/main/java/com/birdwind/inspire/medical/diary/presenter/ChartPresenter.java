package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChartResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChartView;

public class ChartPresenter extends AbstractPresenter<ChartView> {
    public ChartPresenter(ChartView baseView) {
        super(baseView);
    }

    public void getChartData(long uid) {
        initMap();

        paramsMap.put("UID", uid);

        addDisposable(
            apiServer.executePostFormUrlEncode(DoctorApiServer.GET_PATIENT_TESTREPORT.valueOfName(), paramsMap,
                fieldMap, headerMap),
            new AbstractObserver<ChartResponse>(this, baseView, "GetChartData", null, ChartResponse.class, true) {
                @Override
                public void onSuccess(ChartResponse response) {
                    baseView.onGetChart(true, response.getJsonData());
                }
            });
    }
}
