package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentFamilyMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class FamilyMainFragment extends AbstractFragment<AbstractPresenter, FragmentFamilyMainBinding> {
    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentFamilyMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentFamilyMainBinding.inflate(getLayoutInflater());
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
    public void doSomething() {}
}
