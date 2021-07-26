package com.birdwind.init.view.fragment;

import com.birdwind.init.base.view.AbstractFragment;
import com.birdwind.init.databinding.ExampleLoginBinding;
import com.birdwind.init.presenter.ExamplePresenter;
import com.birdwind.init.view.viewCallback.ExampleView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class ExampleFragment extends AbstractFragment<ExamplePresenter, ExampleLoginBinding> implements ExampleView {

    @Override
    public ExamplePresenter createPresenter() {
        return new ExamplePresenter(this);
    }

    @Override
    public ExampleLoginBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ExampleLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}
}
