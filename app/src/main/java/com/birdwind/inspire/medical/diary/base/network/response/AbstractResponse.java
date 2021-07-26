package com.diary.init.base.network.response;

public class AbstractResponse<BR extends BaseResponse> implements BaseSystemResponse {

    private boolean IsSuccess;

    private String Code;

    private String Title;

    private String Message;

    private BR JsonData;

    @Override
    public boolean isSuccess() {
        return IsSuccess;
    }

    @Override
    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    @Override
    public String getCode() {
        return Code;
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public void setCode(String code) {
        Code = code;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public void setMessage(String message) {
        Message = message;
    }

    public BR getJsonData() {
        return JsonData;
    }

    public void setJsonData(BR jsonData) {
        JsonData = jsonData;
    };
}
