package com.birdwind.inspire.medical.diary.model;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;

public class UserModel implements BaseModel {

    private String token;

    private String name;

    private String id;

    private String account;

    private String avatar;

    private boolean isUpdateFCM;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isUpdateFCM() {
        return isUpdateFCM;
    }

    public void setUpdateFCM(boolean updateFCM) {
        isUpdateFCM = updateFCM;
    }
}
