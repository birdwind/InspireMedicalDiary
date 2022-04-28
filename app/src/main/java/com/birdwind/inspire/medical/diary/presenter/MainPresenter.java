package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.MainView;

public class MainPresenter extends AbstractPresenter<MainView> {
    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    public void setPatientName(String name) {
        initMap();

        paramsMap.put("Name", name);

        addDisposable(
            apiServer.executePostFormUrlEncode(PatientApiServer.CHANGE_PATIENT_NAME.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<Response>(this, baseView, "SetPatientName", null, Response.class, true) {
                @Override
                public void onSuccess(Response response) {
                    baseView.onSetPatientName(true);
                }
            });
    }

}
