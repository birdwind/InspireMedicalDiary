package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.server.MessageApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.birdwind.inspire.medical.diary.view.viewCallback.ChatView;

public class ChatPresenter extends AbstractPresenter<ChatView> {
    public ChatPresenter(ChatView baseView) {
        super(baseView);
    }

    public void getChatMessage(long uid) {
        initMap();

        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            paramsMap.put("PID", uid);
        }

        addDisposable(apiServer.executeGet(MessageApiServer.GET_MESSAGE.valueOfName(), paramsMap, headerMap),
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
        initMap();
        paramsMap.put("Message", message);
        if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
            paramsMap.put("PID", uid);
        }

        addDisposable(apiServer.executePostFormUrlEncode(MessageApiServer.SEND_MESSAGE.valueOfName(), paramsMap,
            fieldMap, headerMap),
            new AbstractObserver<Response>(this, baseView, "SendMessage", null, Response.class, false) {
                @Override
                public void onSuccess(Response response) {
                    baseView.onSendMessage(response.isSuccess());
                }
            });
    }

    public void sendScheduleMessage(long uid, String message) {
        initMap();
        paramsMap.put("Message", message);
        paramsMap.put("PID", uid);

        addDisposable(
            apiServer.executePostFormUrlEncode(MessageApiServer.SEND_SEE_ME_TO_PATIENT.valueOfName(), paramsMap,
                fieldMap, headerMap),
            new AbstractObserver<Response>(this, baseView, "SendScheduleMessage", null, Response.class, false) {
                @Override
                public void onSuccess(Response response) {
                    baseView.onSendMessage(response.isSuccess());
                }
            });
    }
}
