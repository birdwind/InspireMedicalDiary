package com.birdwind.inspire.medical.diary.model;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.model.BaseModel;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;

public class UserModel implements BaseModel {

    private int uid;

    private String token;

    private String name;

    private String photoUrl;

    private boolean hasFamily;

    private boolean isUpdateFCM;

    private boolean isProxy;

    private IdentityEnums identityEnums;

    private DiseaseEnums diseaseEnums;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isHasFamily() {
        return hasFamily;
    }

    public void setHasFamily(boolean hasFamily) {
        this.hasFamily = hasFamily;
    }

    public boolean isUpdateFCM() {
        return isUpdateFCM;
    }

    public void setUpdateFCM(boolean updateFCM) {
        isUpdateFCM = updateFCM;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public IdentityEnums getIdentityEnums() {
        return identityEnums;
    }

    public void setIdentityEnums(IdentityEnums identityEnums) {
        this.identityEnums = identityEnums;
    }

    public DiseaseEnums getDiseaseEnums() {
        return diseaseEnums;
    }

    public void setDiseaseEnums(DiseaseEnums diseaseEnums) {
        this.diseaseEnums = diseaseEnums;
    }

    public @ColorInt int getIdentityMainColor() {
        switch (identityEnums) {
            case DOCTOR:
                return ContextCompat.getColor(App.getAppContext(), R.color.colorBlue_009CB2);
            case FAMILY:
                return ContextCompat.getColor(App.getAppContext(), R.color.colorRed_B70908);
            case PAINTER:
            default:
                return ContextCompat.getColor(App.getAppContext(), R.color.colorOrange_DC7400);
        }
    }

    public int getIdentityMainColorId() {
        switch (identityEnums) {
            case DOCTOR:
                return R.color.colorBlue_009CB2;
            case FAMILY:
                return R.color.colorRed_B70908;
            case PAINTER:
            default:
                return R.color.colorOrange_DC7400;
        }
    }
}
