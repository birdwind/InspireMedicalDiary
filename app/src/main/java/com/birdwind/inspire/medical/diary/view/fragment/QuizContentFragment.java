package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizBinding;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizContentBinding;
import com.birdwind.inspire.medical.diary.databinding.FragmentSettingBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class QuizContentFragment extends AbstractFragment<AbstractPresenter, FragmentQuizBinding> {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentQuizBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentQuizBinding.inflate(getLayoutInflater());
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
    public void doSomething() {
    }
}
