package com.birdwind.inspire.medical.diary.base.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.basic.BaseView;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.SharedPreferencesUtils;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingBaseDialog;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingConstant;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingFlower;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.sqlLite.DatabaseConfig;
import com.birdwind.inspire.medical.diary.view.dialog.CommonDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.BaseCustomView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class AbstractActivity<P extends AbstractPresenter, VB extends ViewBinding> extends AppCompatActivity
    implements BaseView, BaseCustomView, BaseActivity<P, VB> {

    public Context context;

    private String className;

    private LoadingBaseDialog loadingDialog;

    protected P presenter;

    protected VB binding;

    public CompositeDisposable compositeDisposable;

    protected RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        className = setClassName();
        presenter = createPresenter();
        rxPermissions = new RxPermissions(this);

        binding = getViewBinding(null, null, false);
        setContentView(binding.getRoot());
        SystemUtils.setSystemTextDefault(context);
        SystemUtils.setDefaultDisplay(context);

        initData(savedInstanceState);
        initView();
        addListener();
        doSomething();
    }

    @Override
    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addDisposable(Disposable mDisposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(mDisposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.eTAG(className, "onStop");
        clearDisposable();
        closeLoadingDialog();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showFileDialog() {
        // TODO:Show File Dialog
        if (!isFinishing()) {
            loadingDialog = new LoadingFlower.Builder(this).direction(LoadingConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE).text("正在下載中,請稍後").fadeColor(Color.DKGRAY).build();
            loadingDialog.show();
        }
    }

    @Override
    public void hideFileDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        // TODO:Hide File Dialog
    }

    @Override
    public void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        // TODO:Close Loading Dialog
    }

    @Override
    public void showLoadingDialog() {
        if (!isFinishing()) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingFlower.Builder(context).direction(LoadingConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();
            }
            loadingDialog.show();
        }
        // TODO:Show Loading Dialog
    }

    @Override
    public void showLoading() {
        if (!isFinishing()) {
            showLoadingDialog();
        }
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showMessage(String title, String msg, boolean isDialog, CommonDialogListener commonDialogListener) {
        if (msg == null)
            msg = "";
        if (!isDialog) {
            showToast(msg);
        } else {
            showDialog(title, msg, commonDialogListener);
        }
    }

    @Override
    public void showLoadingFileDialog() {
        showFileDialog();
    }

    @Override
    public void hideLoadingFileDialog() {
        hideFileDialog();
    }

    @Override
    public void onDownloadProgress(long totalSize, long downSize) {
        // if (dialog != null) {
        // dialog.setProgress((int) (downSize * 100 / totalSize));
        // }

        // TODO:Show Download Progress
    }

    /**
     * activity跳轉（無代參數）
     *
     * @param className class名稱
     */
    @Override
    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity跳轉（有代參數）
     *
     * @param className class名稱
     * @param bundle bundle
     */
    @Override
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(context, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * activity跳轉（無代參數）並結束（）
     *
     * @param className class名稱
     */
    @Override
    public void startActivityWithFinish(Class<?> className) {
        startActivityWithFinish(className, null);
    }

    /**
     * activity跳轉（有代參數）並結束（）
     *
     * @param className class名稱
     * @param bundle bundle
     */
    @Override
    public void startActivityWithFinish(Class<?> className, Bundle bundle) {
        startActivity(className, bundle);
        finish();
    }

    public void logout() {
        logout(null);
    }

    /**
     * activity跳轉至登入（）
     */
    @Override
    public void logout(Bundle bundle) {
        // TODO: Logout
        showToast("全域登出尚未實作");
    }

    @Override
    public void onLogoutSuccess() {
        DatabaseConfig.getInstance(context).clearAllTables();
        SharedPreferencesUtils.put(Config.USER_MODEL_NAME, null);
        SharedPreferencesUtils.put(Config.UNI_PASS, null);
        App.userModel = null;
        startActivityWithFinish(BottomNavigationView.class);
    }

    @Override
    public void onLoginError(String msg) {
        if (!isFinishing()) {
            CommonDialog autoLogoutDialog = new CommonDialog(context, new CommonDialogListener() {
                @Override
                public void clickConfirm() {
                    logout();
                }

                @Override
                public void clickCancel() {
                    logout();
                }

                @Override
                public void onLoginError(String msg) {

                }

                @Override
                public void onServerShutDown() {

                }
            }, context.getString(R.string.common_dialog_title), msg);
            autoLogoutDialog.setCanceledOnTouchOutside(false);
            autoLogoutDialog.show();
        }
    }

    @Override
    public void onServerShutDown() {
        showDialog(context.getString(R.string.common_dialog_title), getString(R.string.error_common_server_shutdown),
            new CommonDialogListener() {
                @Override
                public void clickConfirm() {
                    finish();
                }

                @Override
                public void clickCancel() {

                }

                @Override
                public void onLoginError(String msg) {

                }

                @Override
                public void onServerShutDown() {

                }
            });
    }

    public void switchFragment(int containerViewId, Fragment fragment, FragmentTransaction fragmentTransaction) {
        switchFrag(containerViewId, fragment, fragmentTransaction);
    }

    public void switchFragmentWithBack(int containerViewId, Fragment fragment,
        FragmentTransaction fragmentTransaction) {
        String backStateName = fragment.getClass().getSimpleName();
        fragmentTransaction.addToBackStack(backStateName);
        switchFrag(containerViewId, fragment, fragmentTransaction);
    }

    private void switchFrag(int containerViewId, Fragment fragment, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public void popBackAllFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.eTAG(className, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.eTAG(className, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        closeLoadingDialog();
        LogUtils.eTAG(className, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.eTAG(className, "onStop");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftKeyboard(v);
                return super.dispatchTouchEvent(ev);
            }
        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void getPermission(String[] permissionArray, PermissionRequestListener permissionRequestListener) {
        addDisposable(rxPermissions.requestEach(permissionArray)
            .subscribe(permission -> permissionRequestListener.permissionRequest(context, permission)));
    }

    public boolean hasPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public interface PermissionRequestListener {
        default void permissionRequest(Context context, Permission permission) {
            if (permission.granted) {

            } else if (permission.shouldShowRequestPermissionRationale) {
                ToastUtils.show(context, context.getString(R.string.error_common_permission_denied_some));
            } else {
                ToastUtils.show(context, context.getString(R.string.error_common_permission_never_show));
            }
        }
    }

    public void showDialog(String msg, CommonDialogListener commonDialogListener) {
        showDialog(getString(R.string.common_dialog_title), msg, commonDialogListener);
    }

    public void showDialog(String title, String msg, CommonDialogListener commonDialogListener) {
        if (!isFinishing()) {
            if (commonDialogListener == null) {
                commonDialogListener = new CommonDialogListener() {
                    @Override
                    public void clickConfirm() {

                    }

                    @Override
                    public void clickCancel() {

                    }

                    @Override
                    public void onLoginError(String msg) {
                        AbstractActivity.this.onLoginError(msg);
                    }

                    @Override
                    public void onServerShutDown() {
                        AbstractActivity.this.onServerShutDown();
                    }
                };
            }
            CommonDialog commonDialog = new CommonDialog(context, commonDialogListener, title, msg);
            commonDialog.show();
        }
    }

    @Override
    public void showUpdate(String msg) {
        // TODO:Show Update Dialog
        // UpdateDialog updateDialog = new UpdateDialog(context);
        // updateDialog.show();
    }

    @Override
    public void showFunctionNotComplete(boolean isNeedBake) {
        showDialog(getString(R.string.common_dialog_title), getString(R.string.function_not_complete),
            new CommonDialogListener() {
                @Override
                public void clickConfirm() {
                    if (isNeedBake) {
                        onBackPressed();
                    }
                }

                @Override
                public void clickClose() {

                    if (isNeedBake) {
                        onBackPressed();
                    }
                }
            });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context newContext = newBase;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DisplayMetrics displayMetrics = App.getAppContext().getResources().getDisplayMetrics();
            Configuration configuration = App.getAppContext().getResources().getConfiguration();

            if (configuration.fontScale != 1) {
                configuration.fontScale = 1;
            }

            if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                // Current density is different from Default Density. Override it
                configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
            }

            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            configuration.screenWidthDp = (int) dpWidth;
            configuration.screenHeightDp = (int) dpHeight;

            LogUtils.e("螢幕解析度", String.valueOf(configuration.densityDpi));
            LogUtils.e("文字大小", String.valueOf(configuration.fontScale));
            LogUtils.e("螢幕寬DP", String.valueOf(configuration.screenWidthDp));
            LogUtils.e("螢幕長DP", String.valueOf(configuration.screenHeightDp));

            newContext = newBase.createConfigurationContext(configuration);

        }
        super.attachBaseContext(newContext);
    }

    @Override
    public void onApiComplete(String requestFunction) {

    }

    @Override
    public void showToast(String s) {
        ToastUtils.show(context, s);
    }
}