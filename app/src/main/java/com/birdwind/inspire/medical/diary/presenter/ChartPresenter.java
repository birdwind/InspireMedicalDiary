package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChartResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChartView;

public class ChartPresenter extends AbstractPresenter<ChartView> {
    public ChartPresenter(ChartView baseView) {
        super(baseView);
    }

    public void getChartData(long uid, boolean isFamily) {
        String api = "";
        initMap();
        switch (App.userModel.getIdentityEnums()){
            case DOCTOR:
                api = DoctorApiServer.GET_PATIENT_TEST_REPORT.valueOfName();
                break;
            case FAMILY:
                if(isFamily){
                    api = FamilyApiServer.GET_TEST_REPORT.valueOfName();
                }else{
                    api = PatientApiServer.GET_TEST_REPORT.valueOfName();
                }
                break;
            case PAINTER:
                api = PatientApiServer.GET_TEST_REPORT.valueOfName();
                break;
        }
        paramsMap.put("UID", uid);

        addDisposable(
            apiServer.executePostFormUrlEncode(api, paramsMap,
                fieldMap, headerMap),
            new AbstractObserver<ChartResponse>(this, baseView, "GetChartData", null, ChartResponse.class, true) {
                @Override
                public void onSuccess(ChartResponse response) {
                    baseView.onGetChart(true, response.getJsonData());
                }
            });
    }
}
