package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityDoctorMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.FriendFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;
import com.birdwind.inspire.medical.diary.view.viewCallback.FriendView;
import com.leaf.library.StatusBarUtil;

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
        binding.llScanDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_SCAN);

        });
        binding.llQrcodeDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_QRCODE);

        });
        binding.llReportDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_REPORT);

        });
        binding.llSettingDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_SETTING);

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
        return binding.llMenuDoctorMainActivity;
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
        swipeFragment(PAGE_FRIEND);
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
