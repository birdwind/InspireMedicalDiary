package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizPerkinsPatientBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

public class QuizPerkinsPatientFragment extends AbstractFragment<AbstractPresenter, FragmentQuizPerkinsPatientBinding> implements ToolbarCallback {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentQuizPerkinsPatientBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuizPerkinsPatientBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {}

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}

    @Override
    public String setRightButtonText() {
        return getString(R.string.quiz_submit);
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public void clickTopBarRightTextButton(View view) {

    }
}
