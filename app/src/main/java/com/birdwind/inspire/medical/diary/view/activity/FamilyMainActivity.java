package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityFamilyMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.ChatFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class FamilyMainActivity extends AbstractMainActivity<AbstractPresenter, ActivityFamilyMainBinding>
    implements AbstractActivity.PermissionRequestListener {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityFamilyMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityFamilyMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.componentTopBarFamilyMainActivity.llBackTopBarComp.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.llScanFamilyMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_SCAN);

        });
        binding.llQrcodeFamilyMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_QRCODE);

        });
        binding.llReportFamilyMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_THREE);

        });
        binding.llVisitFamilyMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_THREE);

        });
        binding.llSettingFamilyMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_FOUR);

        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void doSomething() {
//        swipeFragment(PAGE_DEFAULT);
    }

//    @Override
//    protected View setTopBar() {
//        return binding.componentTopBarFamilyMainActivity.rlBackgroundTopBarComp;
//    }
//
//    @Override
//    protected View setTopMenu() {
//        return binding.llMenuFamilyMainActivity;
//    }
//
//    @Override
//    protected View setFragmentView() {
//        return binding.flMainFamilyMainActivity;
//    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ChatFragment());
        fragmentList.add(new ScanFragment());
        fragmentList.add(new QRCodeFragment());
        fragmentList.add(new ReportFragment());
        fragmentList.add(new SettingFragment());
        return fragmentList;
    }
}
