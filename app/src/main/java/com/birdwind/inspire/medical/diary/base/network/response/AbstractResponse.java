package com.birdwind.inspire.medical.diary.base.network.response;

public class AbstractResponse<BR extends BaseResponse> implements BaseSystemResponse {

    private boolean isSuccess;

    private String message;

    private BR jsonData;

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
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public BR getJsonData() {
        return jsonData;
    }

    public void setJsonData(BR jsonData) {
        this.jsonData = jsonData;
    }
}
