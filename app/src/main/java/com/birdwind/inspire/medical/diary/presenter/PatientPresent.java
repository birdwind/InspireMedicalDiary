package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientView;

import java.util.HashMap;

public class PatientPresent extends AbstractPresenter<PatientView> {
    public PatientPresent(PatientView baseView) {
        super(baseView);
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
}
