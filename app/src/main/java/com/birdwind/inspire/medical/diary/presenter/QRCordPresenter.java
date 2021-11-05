package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.CheckDiseaseResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.QRCodeView;

public class QRCordPresenter extends AbstractPresenter<QRCodeView> {
    public QRCordPresenter(QRCodeView baseView) {
        super(baseView);
    }

    public void checkDisease() {
        initMap();

        addDisposable(
            apiServer.executePostFormUrlEncode(PatientApiServer.CHECK_MY_DISEASE.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<CheckDiseaseResponse>(this, baseView, "CheckDisease", null, CheckDiseaseResponse.class,
                true) {
                @Override
                public void onSuccess(CheckDiseaseResponse response) {
                    if (response.getJsonData() != null) {
                        baseView.checkDisease(true, response.getJsonData());
                    }
                }
            });
    }
}
