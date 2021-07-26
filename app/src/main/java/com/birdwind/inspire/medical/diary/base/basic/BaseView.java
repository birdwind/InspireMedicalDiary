package com.birdwind.inspire.medical.diary.base.basic;

import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showLoadingFileDialog();

    void hideLoadingFileDialog();

    void onDownloadProgress(long totalSize, long downSize);

    void showMessage(String title, String msg, boolean isDialog, CommonDialogListener commonDialogListener);

    void showUpdate(String msg);

    void showFunctionNotComplete(boolean isNeedBack);

    void onLoginError(String msg);

    void onServerShutDown();

    void onApiComplete(String requestFunction);
}
