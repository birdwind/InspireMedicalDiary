package com.birdwind.inspire.medical.diary.model.request;

import com.birdwind.inspire.medical.diary.base.network.request.AbstractRequest;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;

public class ExampleRequest extends AbstractRequest {

    public ExampleRequest(String AC, String pass) {
        this.AC = AC;
        Pass = pass;
        this.SDK = String.valueOf(SystemUtils.getAndroidVersion());
        Model = SystemUtils.getDeviceBrand() + "_" + SystemUtils.getSystemModel();
        // if(SharedPreferencesUtils.get(Config.FCM_NAME, "").isEmpty()){
        // SharedPreferencesUtils.put(Config.FCM_NAME, UUID.randomUUID().toString());
        // }
        // this.FCM = SharedPreferencesUtils.get(Config.FCM_NAME, "");
        this.FCM = SystemUtils.initUniquePass();
    }

    private String AC;

    private String Pass;

    private String SDK;

    private String Model;

    private String FCM;

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
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

    public String getFCM() {
        return FCM;
    }

    public void setFCM(String FCM) {
        this.FCM = FCM;
    }
}
