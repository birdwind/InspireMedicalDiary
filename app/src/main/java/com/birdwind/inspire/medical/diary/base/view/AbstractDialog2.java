package com.birdwind.inspire.medical.diary.base.view;

import com.birdwind.inspire.medical.diary.base.basic.BaseDialogListener;
import com.birdwind.inspire.medical.diary.base.basic.BaseView;
import com.birdwind.inspire.medical.diary.base.exception.NotHandleException;
import com.birdwind.inspire.medical.diary.base.utils.analytics.FirebaseAnalyticUtils;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingBaseDialog;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingConstant;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingFlower;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.CommonDialog;
import com.birdwind.inspire.medical.diary.view.dialog.UpdateDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.BaseCustomView;
import java.util.Objects;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

public abstract class AbstractDialog2<C extends BaseDialogListener, P extends AbstractPresenter, VB extends ViewBinding>
    extends DialogFragment implements BaseDialog<P, VB>, BaseView, BaseCustomView {

    protected Context context;

    private LoadingBaseDialog loadingDialog;

    private CommonDialog commonDialog;

    protected C dialogListener;

    protected P presenter;

    protected VB binding;

    public AbstractDialog2(@NonNull Context context) {
        this(context, null);
    }

    public AbstractDialog2(@NonNull Context context, C dialogListener) {
        init(context, dialogListener);
    }

    private void init(Context context, C dialogListener) {
        this.context = context;
        this.dialogListener = dialogListener;
        presenter = createPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = requireActivity().getWindow();
        binding = getViewBinding();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        addListener();
        doSomething();
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void showLoading() {
        if (!((Activity) context).isFinishing()) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingFlower.Builder(context).direction(LoadingConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();
            }
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showLoadingFileDialog() {
        if (!((Activity) context).isFinishing()) {
            loadingDialog = new LoadingFlower.Builder(context).direction(LoadingConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE).text("正在下載中,請稍後").fadeColor(Color.DKGRAY).build();
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoadingFileDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDownloadProgress(long totalSize, long downSize) {
        // TODO:Show Download Progress
    }

    @Override
    public void showMessage(String title, String msg, boolean isDialog, CommonDialogListener baseDialogListener) {
        if (msg == null)
            msg = "";
        if (!((Activity) context).isFinishing()) {
            commonDialog = new CommonDialog(context, title, msg);
            commonDialog.show();
            dismiss();
        }
    }

    @Override
    public void onLoginError(String msg) {
        dialogListener.onLoginError(msg);
    }

    @Override
    public void onServerShutDown() {
        dialogListener.onServerShutDown();
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
        FirebaseAnalyticUtils.setCurrentScreen(getActivity(), getClass().getSimpleName(), null);
    }

    @Override
    public void showUpdate(String msg) {
        UpdateDialog updateDialog = new UpdateDialog(context);
        updateDialog.show();
    }

    @Override
    public void showFunctionNotComplete(boolean isNeedBack) {
        try {
            throw new NotHandleException("Dialog can't support not complete dialog");
        } catch (NotHandleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLogoutSuccess() {
        ((AbstractActivity) context).onLogoutSuccess();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                hideSoftKeyboard(v);
//                return super.dispatchTouchEvent(ev);
//            }
//        }
//
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public void onApiComplete(String requestFunction) {

    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 獲取輸入框當前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            float eventX = event.getX();
            float eventY = event.getY();
            // 點選的是輸入框區域，保留點選EditText的事件
            return !(eventX > left) || !(eventX < right) || !(eventY > top) || !(eventY < bottom);
        }
        return false;
    }
}
