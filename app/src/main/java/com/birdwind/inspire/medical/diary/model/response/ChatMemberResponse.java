package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.birdwind.inspire.medical.diary.model.ChatMemberModel;

import java.util.List;

public class ChatMemberResponse extends AbstractResponse<ChatMemberResponse.Response> {
    public static class Response implements BaseResponse {
        // private String Doctor;
        private List<ChatMemberModel> Family;

        public List<ChatMemberModel> getFamily() {
            return Family;
        }

        public void setFamily(List<ChatMemberModel> family) {
            Family = family;
        }
    }
}
