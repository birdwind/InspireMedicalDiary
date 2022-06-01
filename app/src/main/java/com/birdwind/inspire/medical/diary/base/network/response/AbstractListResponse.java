package com.birdwind.inspire.medical.diary.base.network.response;

import java.util.ArrayList;

public abstract class AbstractListResponse<BR extends Object> implements BaseSystemResponse {

    private boolean IsSuccess;

    private String Message;

    private String Ver;

    private ArrayList<BR> JsonData;

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
        return this.Message;
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

    public ArrayList<BR> getJsonData() {
        return this.JsonData;
    }

    public void setJsonData(ArrayList<BR> jsonData) {
        this.JsonData = jsonData;
    }
}
