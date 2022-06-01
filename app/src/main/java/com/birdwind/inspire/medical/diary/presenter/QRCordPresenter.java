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
            apiServer.executeGet(PatientApiServer.CHECK_DISEASE.valueOfName(), paramsMap,
                headerMap),
            new AbstractObserver<CheckDiseaseResponse>(this, baseView, "CheckDisease", null, CheckDiseaseResponse.class,
                true) {
                @Override
                public void onSuccess(CheckDiseaseResponse response) {
                    if (response.getJsonData() != null) {
                        baseView.checkDisease(true, response.getJsonData());
                    }
                }

                @Override
                public boolean onErrorHandler(String title, String code, String msg, boolean isDialog, CheckDiseaseResponse response) {
                    if(msg.equals("Not Disease")){
                        return true;
                    }
                    return super.onErrorHandler(title, code, msg, isDialog, response);
                }
            });
    }
}
