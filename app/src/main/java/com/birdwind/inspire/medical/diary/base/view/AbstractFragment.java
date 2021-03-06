package com.birdwind.inspire.medical.diary.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.basic.BaseView;
import com.birdwind.inspire.medical.diary.base.enums.ErrorCodeEnums;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.utils.analytics.FirebaseAnalyticUtils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentNavigationListener;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingBaseDialog;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingConstant;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingFlower;
import com.birdwind.inspire.medical.diary.base.view.loadingDialog.LoadingPie;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.activity.BottomNavigationActivity;
import com.birdwind.inspire.medical.diary.view.activity.WebViewActivity;
import com.birdwind.inspire.medical.diary.view.dialog.CommonDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.BaseCustomView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class AbstractFragment<P extends AbstractPresenter, VB extends ViewBinding> extends Fragment
    implements BaseView, BaseFragment<P, VB>, BaseCustomView, FragmentBackHandler {
    public Context context;

    private String className;

    private LoadingBaseDialog loadingDialog;

    protected LoadingPie progressLoadingDialog;

    protected P presenter;

    protected VB binding;

    public CompositeDisposable compositeDisposable;

    protected FragmentNavigationListener fragmentNavigationListener;

    protected CommonDialog commonDialog;

    protected ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.eTAG(className, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        LogUtils.eTAG(className, "onCreateView");
        // binding = getViewBinding(inflater, container, false);
        binding = getViewBinding(inflater, container, false);
        presenter = createPresenter();
        context = getActivity();
        className = setClassName();
        initData(savedInstanceState);
        initView();
        activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result != null) {
                    onActivityResult(result);
                }
            });

        addListener();
        doSomething();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.eTAG(className, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
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
    public void showFileDialog() {
        // TODO:Show File Dialog
        if (!((Activity) context).isFinishing()) {
            progressLoadingDialog = new LoadingPie.Builder(context).ringColor(Color.WHITE).pieColor(Color.WHITE)
                .updateType(LoadingConstant.PIE_MANUAL_UPDATE).build();
            progressLoadingDialog.show();
        }
        // dialog = new ProgressDialog(context);
        // dialog.setMessage("???????????????,?????????");
        // dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // dialog.setCanceledOnTouchOutside(false);
        // dialog.setMax(100);
        // dialog.show();
    }

    @Override
    public void hideFileDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showLoadingDialog() {
        if (!((Activity) context).isFinishing()) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingFlower.Builder(context).direction(LoadingConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE).fadeColor(Color.DKGRAY).build();
            }
            loadingDialog.show();
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showMessage(String title, String msg, boolean isDialog, CommonDialogListener commonDialogListener) {
        if (msg == null)
            msg = "";
        if (title == null) {
            title = getString(R.string.common_dialog_title);
        }
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
        // if (loadingDialog != null) {
        // loadingDialog
        // }
        // TODO: Set Download Progress
    }

    /**
     * activity????????????????????????
     *
     * @param className class??????
     */
    @Override
    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity????????????????????????
     *
     * @param className class??????
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
     * activity???????????????????????????????????????
     *
     * @param className class??????
     */
    @Override
    public void startActivityWithFinish(Class<?> className) {
        startActivityWithFinish(className, null);
    }

    /**
     * activity???????????????????????????????????????
     *
     * @param className class??????
     * @param bundle bundle
     */
    @Override
    public void startActivityWithFinish(Class<?> className, Bundle bundle) {
        startActivity(className, bundle);
        getActivity().finish();
    }

    public void pushFragment(Fragment fragment) {
        if (context instanceof FragmentNavigationListener) {
            ((FragmentNavigationListener) context).pushFragment(fragment);
        } else {
            LogUtils.e("?????????????????????????????????UI!");
        }
    }

    public void replaceFragment(Fragment fragment) {
        if (context instanceof FragmentNavigationListener) {
            ((FragmentNavigationListener) context).replaceFragment(fragment, false);
        } else {
            LogUtils.e("?????????????????????????????????UI!");
        }
    }

    public void popIndexTabFragment(int tab) {
        if (context instanceof FragmentNavigationListener) {
            ((FragmentNavigationListener) context).popIndexTabFragment(tab);
        } else {
            LogUtils.e("?????????????????????????????????UI!");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigationListener) {
            fragmentNavigationListener = (FragmentNavigationListener) context;
        }
        LogUtils.eTAG(className, "OnAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.eTAG(className, "onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.eTAG(className, "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNeedFirebaseAnalyticViewTracker()) {
            FirebaseAnalyticUtils.setCurrentScreen((Activity) context, getClass().getSimpleName(), null);
        }
        if (context instanceof FragmentNavigationListener) {
            fragmentNavigationListener.updateToolbar(setTopBarTitle(), setTopBarTitleColor(),
                setTopBarBackgroundColor(), isStatusLightMode(), isShowTopBarBack(), isShowTopBar(),
                isShowRightButton(), setRightButtonText(), setRightImageButton(), setToolbarCallback(),
                setLeftButtonText());

            // ((BottomNavigationActivity) context).getIconBadges();
        }

        LogUtils.eTAG(className, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.eTAG(className, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.eTAG(className, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearDisposable();
        if (commonDialog != null) {
            commonDialog = null;
        }
        LogUtils.eTAG(className, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.eTAG(className, "onDetach");
    }

    @Override
    public void onLoginError(String msg) {
        ((AbstractActivity) context).onLoginError(msg);
    }

    @Override
    public void onServerShutDown() {
        showDialog(context.getString(R.string.common_dialog_title), getString(R.string.error_common_server_shutdown),
            new CommonDialogListener() {
                @Override
                public void clickConfirm() {
                    getActivity().finish();
                }

                @Override
                public void clickCancel() {

                }

                @Override
                public void onLoginError(String msg) {}

                @Override
                public void onServerShutDown() {

                }
            });
    }

    public void getPermission(AbstractActivity.PermissionRequestListener permissionRequestListener,
        String... permissionArray) {
        ((AbstractActivity) context).getPermission(permissionRequestListener, permissionArray);
    }

    public boolean hasPermission(@NonNull String... permission) {
        return ((AbstractActivity) context).hasPermission(permission);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void onBackPressedByActivity() {
        ((AbstractActivity) context).onBackPressed();
    }

    public void onPopBack(int popTabIndex, int popDeep) {
        ((BottomNavigationActivity) context).onPopBack(popTabIndex, popDeep);
    }

    public Bundle getArgumentsBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle;
        } else {
            showMessage(null, "(" + ErrorCodeEnums.NULL_POINT_EXCEPTION.getCode() + ")"
                + ErrorCodeEnums.NULL_POINT_EXCEPTION.getMessage(), false, null);
            return new Bundle();
        }
    }

    public void openWebView(Bundle bundle, String link, int progressWebViewPageStatus) {
        openWebView(bundle, link, false, false, null, progressWebViewPageStatus, null);
    }

    public void openWebView(Bundle bundle, String link, boolean isPost, boolean isMoney, String title,
        int progressWebViewPageStatus, String dialogContent) {
        bundle.putString("link", link);
        bundle.putString("title", title);
        bundle.putBoolean("isPost", isPost);
        bundle.putBoolean("isMoney", isMoney);
        bundle.putInt("pageStatus", progressWebViewPageStatus);
        bundle.putString("dialogContent", dialogContent);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtras(bundle);
        if (isMoney) {
            startActivityForResult(intent, progressWebViewPageStatus);
        } else {
            startActivity(intent);
        }
    }

    public void showDialog(String title, String msg, CommonDialogListener commonDialogListener) {
        if (!((Activity) context).isFinishing()) {
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
                        AbstractFragment.this.onLoginError(msg);
                    }

                    @Override
                    public void onServerShutDown() {
                        AbstractFragment.this.onServerShutDown();
                    }
                };
            }
            commonDialog = new CommonDialog(context, commonDialogListener, title, msg);
            commonDialog.show();
        }
    }

    @Override
    public void showUpdate(String msg) {
        ((AbstractActivity) context).showUpdate(msg);
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
    public void onLogoutSuccess() {
        ((AbstractActivity) context).onLogoutSuccess();
    }

    @Override
    public void onApiComplete(String requestFunction) {

    }

    @Override
    public void showToast(String s) {
        ToastUtils.show(context, s);
    }

    /**
     * Activity??????
     */
    public void onActivityResult(ActivityResult result) {
        LogUtils.d("????????????, ?????????????????????");
    }

    @Override
    public int checkScreenOrientation() {
        Configuration configuration = getResources().getConfiguration();
        return configuration.orientation;
    }
}
