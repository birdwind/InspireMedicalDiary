package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.ExampleLoginBinding;
import com.birdwind.inspire.medical.diary.databinding.FragmentScanBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class ScanFragment extends AbstractFragment<AbstractPresenter, FragmentScanBinding> {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentScanBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentScanBinding.inflate(getLayoutInflater());
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
