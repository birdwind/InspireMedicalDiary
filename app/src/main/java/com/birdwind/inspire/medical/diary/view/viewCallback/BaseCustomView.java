package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.base.basic.BaseView;

public interface BaseCustomView extends BaseView {

    /**
     * 客製化讀取未知錯誤代碼的訊息
     *
     * @param title 錯誤代碼的原API對應的錯誤訊息標題
     * @param msg 錯誤代碼對應的訊息
     * @param errorCode 錯誤代碼
     * @return true 使用預設處理, false 不使用預設處理
     */
    default boolean onGetMsgConstant(String title, String msg, String errorCode) {
        return true;
    }

    /**
     * 登出
     */
    void onLogoutSuccess();
}
