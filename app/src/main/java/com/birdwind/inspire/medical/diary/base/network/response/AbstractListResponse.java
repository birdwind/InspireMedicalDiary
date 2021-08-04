package com.birdwind.inspire.medical.diary.base.network.response;

import java.util.ArrayList;

public class AbstractListResponse<BR extends BaseResponse> implements BaseSystemResponse {

    private boolean isSuccess;

    private String message;

    private ArrayList<BR> jsonData;

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<BR> getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(ArrayList<BR> jsonData) {
        this.jsonData = jsonData;
    }
}
