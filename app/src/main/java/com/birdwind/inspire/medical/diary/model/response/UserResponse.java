package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class UserResponse extends AbstractResponse<UserResponse.Response> {

    public class Response implements BaseResponse {
        private int UID;

        private String Name;

        private String Phone;

        private int Identity;

        private boolean HasFamily;

        private boolean NeedSetPatientName;

        private boolean IsProxy;

        private int Disease;

        private String PhotoUrl;

        private String LoginKey;

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

        public boolean isHasFamily() {
            return HasFamily;
        }

        public void setHasFamily(boolean hasFamily) {
            HasFamily = hasFamily;
        }

        public boolean isNeedSetPatientName() {
            return NeedSetPatientName;
        }

        public void setNeedSetPatientName(boolean needSetPatientName) {
            NeedSetPatientName = needSetPatientName;
        }

        public boolean isProxy() {
            return IsProxy;
        }

        public void setProxy(boolean proxy) {
            IsProxy = proxy;
        }

        public int getDisease() {
            return Disease;
        }

        public void setDisease(int disease) {
            Disease = disease;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }

        public String getLoginKey() {
            return LoginKey;
        }

        public void setLoginKey(String loginKey) {
            LoginKey = loginKey;
        }
    }
}
