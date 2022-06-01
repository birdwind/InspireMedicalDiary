package com.birdwind.inspire.medical.diary.base.network.response;

public interface BaseSystemResponse extends BaseResponse {

    boolean isSuccess();

    void setSuccess(boolean success);

    String getMessage();

    void setMessage(String message);

    String getVer();

    void setVer(String ver);

}
