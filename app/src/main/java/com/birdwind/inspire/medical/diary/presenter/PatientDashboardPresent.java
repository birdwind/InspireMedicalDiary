package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientDashboardView;

import java.util.HashMap;

public class PatientDashboardPresent extends AbstractPresenter<PatientDashboardView> {
    public PatientDashboardPresent(PatientDashboardView baseView) {
        super(baseView);
    }

    public void getChatMember(long uid) {
        String api = "";
        initMap();
        HashMap<String, Object> map = new HashMap<>();
        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                api = DoctorApiServer.GET_PATIENT_MEMBER.valueOfName();
                break;
            case FAMILY:
            case PAINTER:
                api = PatientApiServer.GET_MY_MEMBER.valueOfName();
                break;
        }
        map.put("UID", uid);

        addDisposable(apiServer.executePostFormUrlEncode(api, map, fieldMap, headerMap),
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
