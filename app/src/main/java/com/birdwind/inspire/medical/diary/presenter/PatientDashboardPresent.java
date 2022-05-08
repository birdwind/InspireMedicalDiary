package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatMemberService;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientDashboardView;

import java.util.HashMap;

public class PatientDashboardPresent extends AbstractPresenter<PatientDashboardView> {
    public PatientDashboardPresent(PatientDashboardView baseView) {
        super(baseView);
    }

    public void getChatMember(long uid) {
        initMap();
        HashMap<String, Object> map = new HashMap<>();
        map.put("UID", uid);

        addDisposable(apiServer.executeGet(PatientApiServer.GET_MEMBER.valueOfName(), map, headerMap),
            new AbstractObserver<ChatMemberResponse>(this, baseView, "GetChatMember", null, ChatMemberResponse.class,
                true) {
                @Override
                public void onSuccess(ChatMemberResponse response) {
                    ChatMemberService chatMemberService = new ChatMemberService(context);
                    if (response.getJsonData().getFamily() != null) {
                        chatMemberService.save(response.getJsonData().getFamily());
                    } else {
                        chatMemberService.delete();
                    }
                    baseView.onGetChatMember(true);
                }
            });
    }
}
