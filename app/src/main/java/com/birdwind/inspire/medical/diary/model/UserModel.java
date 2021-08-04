package com.birdwind.inspire.medical.diary.model;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;

public class UserModel implements BaseModel {

    private String token;

    private boolean isUpdateFCM;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUpdateFCM() {
        return isUpdateFCM;
    }

    public void setUpdateFCM(boolean updateFCM) {
        isUpdateFCM = updateFCM;
    }
}
