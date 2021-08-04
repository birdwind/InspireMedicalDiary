package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class LoginResponse extends AbstractResponse<LoginResponse.Response> {

    public class Response implements BaseResponse {
        private int uid;
        private String name;
        private String password;
        private String phone;
        private String timeC;
        private String userC;
        private String timeU;
        private String userU;
        private boolean isEnable;
        private boolean isDoctor;
        private String cont;
        private String loginKey;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTimeC() {
            return timeC;
        }

        public void setTimeC(String timeC) {
            this.timeC = timeC;
        }

        public String getUserC() {
            return userC;
        }

        public void setUserC(String userC) {
            this.userC = userC;
        }

        public String getTimeU() {
            return timeU;
        }

        public void setTimeU(String timeU) {
            this.timeU = timeU;
        }

        public String getUserU() {
            return userU;
        }

        public void setUserU(String userU) {
            this.userU = userU;
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        public boolean isDoctor() {
            return isDoctor;
        }

        public void setDoctor(boolean doctor) {
            isDoctor = doctor;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }
    }
}
