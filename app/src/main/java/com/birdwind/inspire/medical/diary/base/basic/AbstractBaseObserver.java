package com.birdwind.inspire.medical.diary.base.basic;

import android.content.Context;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.response.BaseSystemResponse;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.rxHelper.RxException;

import org.json.JSONObject;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public abstract class AbstractBaseObserver<T extends ResponseBody, BR extends BaseSystemResponse>
    extends DisposableObserver<T> implements BaseObserver<BR> {

    protected BaseView view;

    protected BasePresenter presenter;

    protected Context context = App.getAppContext();

    protected String functionName;

    protected String errorTitle;

    protected Class<BR> claz;

    private boolean isShowLoading;

    public AbstractBaseObserver(BasePresenter presenter, BaseView view, String functionName, String errorTitle,
        Class<BR> claz, boolean isShowLoading) {
        this.presenter = presenter;
        this.view = view;
        this.functionName = functionName;
        this.claz = claz;
        this.isShowLoading = isShowLoading;

        if (errorTitle == null || errorTitle.isEmpty()) {
            this.errorTitle = context.getString(R.string.common_dialog_title);
        } else {
            this.errorTitle = errorTitle;
        }
    }

    @Override
    protected void onStart() {
        if (view != null && isShowLoading) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            String responseJson = o.string();
            LogUtils.e(functionName + "Response", responseJson);

            try {
                JSONObject jsonObject = new JSONObject(responseJson);

                BR response = GsonUtils.parseJsonToBean(jsonObject.toString(), claz);

                if (response == null) {
                    LogUtils.e(functionName + "Error", "Response Not parse to object");
                    onError(errorTitle, "0", context.getString(R.string.error_common_server_data), true);
                } else {
                    if (response.isSuccess()) {
                        onSuccess(response);
                    } else {
                        String title = errorTitle;
                        String message =
                            response.getMessage() == null ? context.getString(R.string.error_common_server_data)
                                : response.getMessage();

                        if (!onErrorHandler(title, null, message, true, response)) {
                            showMsg(title, message, true);
                        }
                    }
                }
            } catch (Exception e) {
                onError(e);
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.exceptionTAG(functionName + "Error", e);
        if (view != null && isShowLoading) {
            view.hideLoading();
        }
        showMsg(functionName + e.getMessage(), true);
        RxException rxException = RxException.handleException(e);
        onError(errorTitle, String.valueOf(rxException.getCode()),
            "(" + rxException.getCode() + ")" + rxException.getMessage(), false);

        if (view != null) {
            view.onApiComplete(functionName);
        }
    }

    @Override
    public void onComplete() {
        if (view != null) {
            if (isShowLoading) {
                view.hideLoading();
            }
            view.onApiComplete(functionName);
        }
    }

    @Override
    public void onError(String title, String code, String msg, boolean isDialog) {
        showMsg(title, msg, isDialog);
        LogUtils.e(functionName + "Error", msg);
    }

    protected void showMsg(String title, String msg, boolean isDialog) {
        if (view != null) {
            view.showMessage(title, msg, isDialog, null);
        }
    }

    protected void showMsg(String msg, boolean isDialog) {
        showMsg(errorTitle, msg, isDialog);
    }
}
