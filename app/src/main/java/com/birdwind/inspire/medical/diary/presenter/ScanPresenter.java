package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScanView;

public class ScanPresenter extends AbstractPresenter<ScanView> {
    public ScanPresenter(ScanView baseView) {
        super(baseView);
    }

    public void checkQRCodeUid(int uid) {
        initMap();
        paramsMap.put("UID", uid);
        String api = "";

        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                api = DoctorApiServer.CHECK_QRCODE_UID.valueOfName();
                break;
            case FAMILY:
                 api = FamilyApiServer.CHECK_QRCODE_UID.valueOfName();
                break;
//            case PAINTER:
//                api = PatientApiServer.CHECK_QRCODE_UID.valueOfName();
//                break;
        }

        addDisposable(apiServer.executeGet(api, paramsMap, headerMap),
            new AbstractObserver<UserResponse>(this, baseView, "CheckQRCodeUid", null, UserResponse.class,
                true) {
                @Override
                public void onSuccess(UserResponse response) {
                    baseView.onCheckUidResponse(true, response);
                }
            });
    }
}
