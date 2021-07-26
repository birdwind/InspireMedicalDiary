package com.birdwind.init.base.view;

import com.birdwind.init.presenter.AbstractPresenter;
import androidx.viewbinding.ViewBinding;

public interface BaseDialog<P extends AbstractPresenter, VB extends ViewBinding> {

    VB getViewBinding();

    void addListener();

    void doSomething();

    P createPresenter();
}
