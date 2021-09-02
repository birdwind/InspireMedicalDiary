package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityPainterMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.ChatFragment;
import com.birdwind.inspire.medical.diary.view.fragment.FriendFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QuizContentFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class PainterMainActivity extends AbstractMainActivity<AbstractPresenter, ActivityPainterMainBinding>
    implements AbstractActivity.PermissionRequestListener {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityPainterMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityPainterMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.componentTopBarPainterMainActivity.llBackTopBarComp.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.llScanPainterMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_SCAN);

        });
        binding.llQrcodePainterMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_QRCODE);

        });
        binding.llQuizPainterMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_THREE);

        });
        binding.llVisitPainterMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_FOUR);

        });
        binding.llSettingPainterMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_FIVE);

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
        swipeFragment(PAGE_DEFAULT);
    }

    @Override
    protected View setTopBar() {
        return binding.componentTopBarPainterMainActivity.rlBackgroundTopBarComp;
    }

    @Override
    protected View setTopMenu() {
        return binding.llMenuPainterMainActivity;
    }

    @Override
    protected View setFragmentView() {
        return binding.flMainPainterMainActivity;
    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ChatFragment());
        fragmentList.add(new ScanFragment());
        fragmentList.add(new QRCodeFragment());
        fragmentList.add(new QuizContentFragment());
        fragmentList.add(new SettingFragment());
        fragmentList.add(new SettingFragment());
        return fragmentList;
    }
}
