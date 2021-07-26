package com.birdwind.inspire.medical.diary.base.view;

import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import androidx.viewbinding.ViewBinding;

public interface BaseDialog<P extends AbstractPresenter, VB extends ViewBinding> {

    VB getViewBinding();

    void addListener();

    void doSomething();

    P createPresenter();
}
