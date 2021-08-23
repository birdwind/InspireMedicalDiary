package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

import java.util.HashMap;

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

        addDisposable(apiServer.executePostFormUrlEncode(api, paramsMap, fieldMap, headerMap),
            new AbstractObserver<ChatResponse>(this, baseView, "GetChatMessages", null, ChatResponse.class, true) {
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

    public void getChatMember(long uid) {
        initMap();
        HashMap<String, Object> map = new HashMap<>();
        map.put("UID", uid);

        addDisposable(
            apiServer.executePostFormUrlEncode(DoctorApiServer.GET_PATIENT_MEMBER.valueOfName(), map, fieldMap,
                headerMap),
            new AbstractObserver<ChatMemberResponse>(this, baseView, "GetChatMember", null, ChatMemberResponse.class,
                true) {
                @Override
                public void onSuccess(ChatMemberResponse response) {
                    ChatMemberService chatMemberService = new ChatMemberService(context);
                    chatMemberService.save(response.getJsonData());
                    baseView.onGetChatMember(true);
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
}
