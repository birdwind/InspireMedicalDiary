package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityDoctorMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.FriendFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class DoctorMainActivity extends AbstractActivity<AbstractPresenter, ActivityDoctorMainBinding> {

    private final int PAGE_FRIEND = 0;

    private final int PAGE_SCAN = 1;

    private final int PAGE_QRCODE = 2;

    private final int PAGE_REPORT = 3;

    private final int PAGE_SETTING = 4;

    private SlideHeightAnimation expandSlideMenuAnimation;

    private SlideHeightAnimation shrinkSlideMenuAnimation;

    private SlideHeightAnimation expandSlideTopBarAnimation;

    private SlideHeightAnimation shrinkSlideTopBarAnimation;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    private FragNavTransactionOptions tabToRightFragNavTransactionOptions;

    private FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    private List<Fragment> fragments;

    private int currentFragmentIndex;

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
            hideTopBar(false);
        });
        binding.llQrcodeDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_QRCODE);
            hideTopBar(false);
        });
        binding.llReportDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_REPORT);
            hideTopBar(false);
        });
        binding.llSettingDoctorMainActivity.setOnClickListener(v -> {
            swipeFragment(PAGE_SETTING);
            hideTopBar(false);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        expandSlideMenuAnimation = new SlideHeightAnimation(binding.llMenuDoctorMainActivity, Utils.dp2px(this, 91),
            Utils.dp2px(this, 126), 300);
        shrinkSlideMenuAnimation = new SlideHeightAnimation(binding.llMenuDoctorMainActivity, Utils.dp2px(this, 126),
            Utils.dp2px(this, 91), 300);
        expandSlideMenuAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideMenuAnimation.setInterpolator(new AccelerateInterpolator());

        expandSlideTopBarAnimation =
            new SlideHeightAnimation(binding.componentTopBarDoctorMainActivity.rlBackgroundTopBarComp,
                Utils.dp2px(this, 0), Utils.dp2px(this, 44), 300);
        shrinkSlideTopBarAnimation =
            new SlideHeightAnimation(binding.componentTopBarDoctorMainActivity.rlBackgroundTopBarComp,
                Utils.dp2px(this, 44), Utils.dp2px(this, 0), 300);
        expandSlideTopBarAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideTopBarAnimation.setInterpolator(new AccelerateInterpolator());

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();

        tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        fragments = new ArrayList<>();
        currentFragmentIndex = -1;
        initFragmentList();
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorBlue_009CB2));
    }

    @Override
    public void doSomething() {
        swipeFragment(PAGE_FRIEND);
    }

    private void hideTopBar(boolean isHide) {
        if (isHide) {
            slideHeightAnimation(binding.componentTopBarDoctorMainActivity.rlBackgroundTopBarComp,
                shrinkSlideTopBarAnimation);
            slideHeightAnimation(binding.llMenuDoctorMainActivity, expandSlideMenuAnimation);
        } else {
            slideHeightAnimation(binding.componentTopBarDoctorMainActivity.rlBackgroundTopBarComp,
                expandSlideTopBarAnimation);
            slideHeightAnimation(binding.llMenuDoctorMainActivity, shrinkSlideMenuAnimation);
        }
    }

    private void initFragmentList() {
        fragments.add(new FriendFragment());
        fragments.add(new ScanFragment());
        fragments.add(new QRCodeFragment());
        fragments.add(new ReportFragment());
        fragments.add(new SettingFragment());
    }

    private void slideHeightAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    private void pushFragment(int pageIndex, boolean isRightToLeft) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isRightToLeft) {
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(binding.flMainDoctorMainActivity.getId(), fragments.get(pageIndex));
        transaction.commit();
    }

    private void swipeFragment(int pageIndex) {
        if (currentFragmentIndex == pageIndex)
            return;
        pushFragment(pageIndex, currentFragmentIndex > pageIndex);
        currentFragmentIndex = pageIndex;
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentIndex != PAGE_FRIEND) {
            swipeFragment(PAGE_FRIEND);
            hideTopBar(true);
        }else{
            super.onBackPressed();
        }
    }
}
