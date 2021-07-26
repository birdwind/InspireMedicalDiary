package com.birdwind.inspire.medical.diary.model.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class ExampleResponse extends AbstractResponse<ExampleResponse.Response> {

    @Entity(tableName = "example")
    public static class Response implements BaseResponse {
        @PrimaryKey
        @NonNull
        private String SMSToken;

        private String LoginToken;

        public String getSMSToken() {
            return SMSToken;
        }

        public void setSMSToken(String SMSToken) {
            this.SMSToken = SMSToken;
        }

        public String getLoginToken() {
            return LoginToken;
        }

        public void setLoginToken(String loginToken) {
            LoginToken = loginToken;
        }
    }
}
