package com.diary.init.base.basic;

import com.diary.init.view.dialog.callback.CommonDialogListener;

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
