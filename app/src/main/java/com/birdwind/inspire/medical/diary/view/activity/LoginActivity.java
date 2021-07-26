package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityLoginBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class LoginActivity extends AbstractActivity<AbstractPresenter, ActivityLoginBinding> {
    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityLoginBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onBackPressed() {
        startActivityWithFinish(AuthActivity.class);
    }
}
