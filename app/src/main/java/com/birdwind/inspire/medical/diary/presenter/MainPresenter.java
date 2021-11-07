package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.MainView;

public class MainPresenter extends AbstractPresenter<MainView> {
    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    public void setPatientName(String name) {
        initMap();

        paramsMap.put("Name", name);

        addDisposable(
            apiServer.executePostFormUrlEncode(FamilyApiServer.CHANGE_PATIENT_NAME.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "SetPatientName", null, AbstractResponse.class,
                true) {
                @Override
                public void onSuccess(AbstractResponse response) {
                    baseView.onSetPatientName(true);
                }
            });
    }

}
