package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityDoctorMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.FriendFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class DoctorMainActivity extends AbstractMainActivity<AbstractPresenter, ActivityDoctorMainBinding>
    implements AbstractActivity.PermissionRequestListener {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityDoctorMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityDoctorMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.componentTopBarDoctorMainActivity.llBackTopBarComp.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected View setTopBar() {
        return binding.componentTopBarDoctorMainActivity.rlBackgroundTopBarComp;
    }

    @Override
    protected View setTopMenu() {
        return null;
    }

    @Override
    protected View setFragmentView() {
        return binding.flMainDoctorMainActivity;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void doSomething() {
        swipeFragment(PAGE_DEFAULT);
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FriendFragment());
        fragmentList.add(new ScanFragment());
        fragmentList.add(new QRCodeFragment());
        fragmentList.add(new ReportFragment());
        fragmentList.add(new SettingFragment());
        return fragmentList;
    }
}
