package com.birdwind.init.base.network.response;

public interface BaseSystemResponse extends BaseResponse {

    boolean isSuccess();

    void setSuccess(boolean success);

    String getCode();

    String getTitle();

    void setTitle(String title);

    void setCode(String code);

    String getMessage();

    void setMessage(String message);
}
