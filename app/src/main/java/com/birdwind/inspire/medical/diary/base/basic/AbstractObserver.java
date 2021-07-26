package com.birdwind.inspire.medical.diary.base.basic;

import com.birdwind.inspire.medical.diary.base.network.response.BaseSystemResponse;

import okhttp3.ResponseBody;

public abstract class AbstractObserver<AR extends BaseSystemResponse> extends AbstractBaseObserver<ResponseBody, AR> {
    public AbstractObserver(BasePresenter presenter, BaseView view, String functionName, String errorTitle, Class<AR> claz,
        boolean isShowLoading) {
        super(presenter, view, functionName, errorTitle, claz, isShowLoading);
    }
}
