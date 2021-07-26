package com.birdwind.init.base.view;

import com.birdwind.init.presenter.AbstractPresenter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.viewbinding.ViewBinding;
import io.reactivex.rxjava3.disposables.Disposable;

public interface BaseMVPView<P extends AbstractPresenter, VB extends ViewBinding> {

    P createPresenter();

    VB getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent);

    void addListener();

    void initData(Bundle savedInstanceState);

    void initView();

    void doSomething();

    default String setClassName() {
        return getClass().getSimpleName();
    }

    void clearDisposable();

    void addDisposable(Disposable mDisposable);

    void showFileDialog();

    void hideFileDialog();

    void closeLoadingDialog();

    void showLoadingDialog();

    void startActivity(Class<?> className);

    void startActivity(Class<?> className, Bundle bundle);

    void startActivityWithFinish(Class<?> className);

    void startActivityWithFinish(Class<?> className, Bundle bundle);

    void showToast(String s);

}
