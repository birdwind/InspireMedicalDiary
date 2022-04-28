package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.server.MyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.BasicInfoView;

public class BasicInfoPresenter extends AbstractPresenter<BasicInfoView> {
    public BasicInfoPresenter(BasicInfoView baseView) {
        super(baseView);
    }

    public void getBasicInfo(int uid) {
        initMap();
        paramsMap.put("UID", uid);
        addDisposable(apiServer.executeGet(MyApiServer.GET_INFORMATION.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<InformationResponse>(this, baseView, "GetInformation", null, InformationResponse.class,
                true) {
                @Override
                public void onSuccess(InformationResponse response) {
                    baseView.onGetInformation(true, response.getJsonData());
                }
            });
    }

    public void settBasicInfo(InformationResponse.Response response) {
        initMap();
        addDisposable(apiServer.executePost(MyApiServer.CHANGE_INFORMATION.valueOfName(), paramsMap, packageToRequestBody(response), headerMap),
            new AbstractObserver<InformationResponse>(this, baseView, "SetInformation", null, InformationResponse.class,
                true) {
                @Override
                public void onSuccess(InformationResponse response) {
                    baseView.onSetInformation(true);
                }
            });
    }
}
