package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.UserDialogView;

public class UserDialogPresenter extends AbstractPresenter<UserDialogView> {
    public UserDialogPresenter(UserDialogView baseView) {
        super(baseView);
    }

    public void addUser(int uid) {
        String api = "";

        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                api = DoctorApiServer.ADD_PATIENT.valueOfName();
                break;
            case FAMILY:
                break;
            case PAINTER:
                api = PatientApiServer.JOIN_MY_GROUP.valueOfName();
                break;
        }

        initMap();
        paramsMap.put("UID", uid);

        addDisposable(apiServer.executePostFormUrlEncode(api, paramsMap, fieldMap, headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "AddUser", null, AbstractResponse.class, true) {
                @Override
                public void onSuccess(AbstractResponse response) {

                }
            });
    }
}
