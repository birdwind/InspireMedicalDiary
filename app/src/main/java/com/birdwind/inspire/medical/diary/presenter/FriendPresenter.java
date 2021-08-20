package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.FriendResponse;
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
                new AbstractObserver<FriendResponse>(this, baseView, "GetPatient", null, FriendResponse.class, true) {
                    @Override
                    public void onSuccess(FriendResponse response) {
                        baseView.onGetFriends(response.getJsonData());
                    }
                });
    }
}
