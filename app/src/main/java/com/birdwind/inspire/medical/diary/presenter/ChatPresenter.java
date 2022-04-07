package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

public class ChatPresenter extends AbstractPresenter<ChatView> {
    public ChatPresenter(ChatView baseView) {
        super(baseView);
    }

    public void getChatMessage(long uid) {
        String api = "";
        initMap();

        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                paramsMap.put("uid", uid);
                api = DoctorApiServer.GET_PATIENT_MESSAGES.valueOfName();
                break;
            case FAMILY:
                api = FamilyApiServer.GET_MY_FAMILY_MESSAGES.valueOfName();
                break;
            case PAINTER:
                api = PatientApiServer.GET_MY_MESSAGES.valueOfName();
                break;
        }

        addDisposable(apiServer.executeGet(api, paramsMap, headerMap),
            new AbstractObserver<ChatResponse>(this, baseView, "GetChatMessages", null, ChatResponse.class, false) {
                @Override
                public void onSuccess(ChatResponse response) {
                    ChatService chatService = new ChatService(context);
                    if (response.getJsonData() != null) {
                        for (ChatResponse.Response item : response.getJsonData()) {
                            chatService.save(item);
                        }
                        baseView.onGetChatMessage(true);
                    }
                }
            });
    }

    public void sendChatMessage(long uid, String message) {
        String api = "";
        initMap();
        paramsMap.put("Message", message);
        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                paramsMap.put("PID", uid);
                api = DoctorApiServer.SEND_MESSAGE_TO_PATIENT.valueOfName();
                break;
            case FAMILY:
                api = FamilyApiServer.SEND_MESSAGE.valueOfName();
                break;
            case PAINTER:
                api = PatientApiServer.SEND_MESSAGE.valueOfName();
                break;
        }

        addDisposable(apiServer.executePostFormUrlEncode(api, paramsMap, fieldMap, headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "SendMessage", null, AbstractResponse.class, false) {
                @Override
                public void onSuccess(AbstractResponse response) {
                    baseView.onSendMessage(response.isSuccess());
                }
            });
    }

    public void sendScheduleMessage(long uid, String message) {
        initMap();
        paramsMap.put("Message", message);
        paramsMap.put("PID", uid);

        addDisposable(
            apiServer.executePostFormUrlEncode(DoctorApiServer.SEND_SEE_ME_TO_PATIENT.valueOfName(), paramsMap,
                fieldMap, headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "SendScheduleMessage", null, AbstractResponse.class,
                false) {
                @Override
                public void onSuccess(AbstractResponse response) {
                    baseView.onSendMessage(response.isSuccess());
                }
            });
    }
}
