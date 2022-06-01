package com.birdwind.inspire.medical.diary.base.network.response;

import java.io.Serializable;

public abstract class AbstractResponse<BR extends BaseResponse> implements Serializable, BaseSystemResponse {

    private boolean IsSuccess;

    private String Message;

    private String Ver;

    private BR JsonData;

    @Override
    public boolean isSuccess() {
        return IsSuccess;
    }

    @Override
    public void setSuccess(boolean success) {
        this.IsSuccess = success;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public void setMessage(String message) {
        this.Message = message;
    }

    @Override
    public String getVer() {
        return this.Ver;
    }

    @Override
    public void setVer(String ver) {
        this.Ver = ver;
    }

    public BR getJsonData() {
        return JsonData;
    }

    public void setJsonData(BR jsonData) {
        this.JsonData = jsonData;
    }
}
