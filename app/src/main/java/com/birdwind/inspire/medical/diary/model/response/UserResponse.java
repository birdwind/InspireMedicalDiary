package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class UserResponse extends AbstractResponse<UserResponse.Response> {

    public class Response implements BaseResponse {
        private int UID;

        private String Name;

        private String Phone;

        private int Identity;

        private String PhotoUrl;

        public int getUID() {
            return UID;
        }

        public void setUID(int UID) {
            this.UID = UID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public int getIdentity() {
            return Identity;
        }

        public void setIdentity(int identity) {
            Identity = identity;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }
    }
}
