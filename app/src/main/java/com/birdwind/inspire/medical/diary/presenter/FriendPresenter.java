package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.FriendView;

public class FriendPresenter extends AbstractPresenter<FriendView> {
    public FriendPresenter(FriendView baseView) {
        super(baseView);
    }

    public void getPatient() {
        initMap();
        addDisposable(
            apiServer.executePostFormUrlEncode(DoctorApiServer.GET_MY_PATIENT.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "Login", null, AbstractResponse.class, true) {
                @Override
                public void onSuccess(AbstractResponse response) {

                }
            });
    }
}
