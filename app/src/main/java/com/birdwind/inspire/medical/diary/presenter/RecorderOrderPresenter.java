package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.RecordOrderResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecorderOrderView;

public class RecorderOrderPresenter extends AbstractPresenter<RecorderOrderView> {
    public RecorderOrderPresenter(RecorderOrderView baseView) {
        super(baseView);
    }

    public void getRecordOrder(long uid) {
        initMap();

        String api = PatientApiServer.GET_VOICE_TEST_LIST.valueOfName();
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            api = DoctorApiServer.GET_PATIENT_VOICE_TEST_LIST.valueOfName();
            paramsMap.put("UID", uid);
        }

        addDisposable(apiServer.executeGet(api, paramsMap, headerMap),
            new AbstractObserver<RecordOrderResponse>(this, baseView, "OnGetRecordOrder", null,
                RecordOrderResponse.class, true) {
                @Override
                public void onSuccess(RecordOrderResponse response) {
                    baseView.onGetPatientVoiceList(true, response.getJsonData());
                }
            });
    }
}
