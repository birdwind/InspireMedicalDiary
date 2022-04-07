package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.DoctorPatientPresent;

public class DoctorPatientPresenter extends AbstractPresenter<DoctorPatientPresent> {
    public DoctorPatientPresenter(DoctorPatientPresent baseView) {
        super(baseView);
    }

    public void getPatient() {
        initMap();
        addDisposable(apiServer.executeGet(DoctorApiServer.GET_MY_PATIENT.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<PatientResponse>(this, baseView, "GetPatient", null, PatientResponse.class, true) {
                @Override
                public void onSuccess(PatientResponse response) {
                    baseView.onGetFriends(response.getJsonData());
                }
            });
    }
}
