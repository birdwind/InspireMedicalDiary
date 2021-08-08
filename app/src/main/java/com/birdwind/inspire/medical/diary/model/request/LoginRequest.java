package com.birdwind.inspire.medical.diary.model.request;

import com.birdwind.inspire.medical.diary.base.network.request.AbstractRequest;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;

import java.util.Map;

public class LoginRequest extends AbstractRequest {

    public LoginRequest(String phone, IdentityEnums identityEnums) {
        this.Phone = phone;
        this.FCM = SystemUtils.initUniquePass();
        this.Identity = identityEnums.getType();
        this.OS = "Android";
        this.SDK = String.valueOf(SystemUtils.getAndroidVersion());
        this.Model = SystemUtils.getDeviceBrand() + "_" + SystemUtils.getSystemModel();
    }

    private String Phone;

    private String FCM;

    private int Identity;

    private String OS;

    private String SDK;

    private String Model;


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFCM() {
        return FCM;
    }

    public void setFCM(String FCM) {
        this.FCM = FCM;
    }

    public int getIdentity() {
        return Identity;
    }

    public void setIdentity(int identity) {
        Identity = identity;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getSDK() {
        return SDK;
    }

    public void setSDK(String SDK) {
        this.SDK = SDK;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Map<String, String> checkLoginRequest() {
        //TODO:檢查登入資訊
        return null;
    }
}
