package com.birdwind.inspire.medical.diary.base.basic;

import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public interface BaseObserver<R extends BaseResponse> {

    /**
     * 成功 預設處理
     */
    void onSuccess(R response);


    /**
     * 錯誤 預設處理
     */
    void onError(String title, String code, String msg, boolean isDialog);

    /**
     * 錯誤處理
     * @param title 錯誤訊息標題
     * @param code 錯誤訊息代碼
     * @param msg 錯誤訊息
     * @param isDialog 是否為Dialog
     * @param response Response
     *
     * @return true 處理完畢，不使用預設處理, false 尚未處理完畢，使用預設處理
     */
    default boolean onErrorHandler(String title, String code, String msg, boolean isDialog, R response){
        return false;
    }

    default void onUpdateVersion(){

    }
}
