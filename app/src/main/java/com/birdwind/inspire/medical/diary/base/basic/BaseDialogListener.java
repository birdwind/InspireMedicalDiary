package com.birdwind.inspire.medical.diary.base.basic;

public interface BaseDialogListener {

    default void clickClose() {}

    default void clickConfirm() {}

    default void clickCancel() {}

    default void onLoginError(String msg) {}

    default void onServerShutDown() {}
}
