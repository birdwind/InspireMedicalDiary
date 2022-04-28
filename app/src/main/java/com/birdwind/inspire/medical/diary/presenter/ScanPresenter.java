package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScanView;

public class ScanPresenter extends AbstractPresenter<ScanView> {
    public ScanPresenter(ScanView baseView) {
        super(baseView);
    }

    public void checkQRCodeUid(int uid) {
        initMap();
        paramsMap.put("UID", uid);

        addDisposable(apiServer.executeGet(PatientApiServer.CHECK_QRCODE_UID.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<UserResponse>(this, baseView, "CheckQRCodeUid", null, UserResponse.class, true) {
                @Override
                public void onSuccess(UserResponse response) {
                    baseView.onCheckUidResponse(true, response);
                }
            });
    }
}
