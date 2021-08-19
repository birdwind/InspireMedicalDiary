package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

public class ChatPresenter extends AbstractPresenter<ChatView> {
    public ChatPresenter(ChatView baseView) {
        super(baseView);
    }

    public void getChatMessage(int uid) {
        String api = "";
        initMap();

        paramsMap.put("uid", uid);

        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                api = DoctorApiServer.GET_PATIENT_MESSAGES.valueOfName();
                break;
            case FAMILY:
                api = FamilyApiServer.GET_MY_FAMILY_MESSAGES.valueOfName();
                break;
            case PAINTER:
                api = PatientApiServer.GET_MY_MESSAGES.valueOfName();
                break;
        }

        addDisposable(apiServer.executePostFormUrlEncode(api, paramsMap, fieldMap, headerMap),
            new AbstractObserver<ChatResponse>(this, baseView, "GetChatMessages", null, ChatResponse.class, true) {
                @Override
                public void onSuccess(ChatResponse response) {

                }
            });

    }
}
