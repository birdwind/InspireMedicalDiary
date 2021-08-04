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
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityDoctorMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.FriendFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ReportFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;
import com.leaf.library.StatusBarUtil;

public class DoctorMainActivity extends AbstractActivity<AbstractPresenter, ActivityDoctorMainBinding> {

    private SlideHeightAnimation expandSlideMenuAnimation;

    private SlideHeightAnimation shrinkSlideMenuAnimation;

    private SlideHeightAnimation expandSlideTopBarAnimation;

    private SlideHeightAnimation shrinkSlideTopBarAnimation;

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
            pushFragment(new FriendFragment());
            hideTopBar(true);
        });
        binding.llScanDoctorMainActivity.setOnClickListener(v -> {
            pushFragment(new ScanFragment());
            hideTopBar(false);
        });
        binding.llQrcodeDoctorMainActivity.setOnClickListener(v -> {
            pushFragment(new QRCodeFragment());
            hideTopBar(false);
        });
        binding.llReportDoctorMainActivity.setOnClickListener(v -> {
            pushFragment(new ReportFragment());
            hideTopBar(false);
        });
        binding.llSettingDoctorMainActivity.setOnClickListener(v -> {
            pushFragment(new SettingFragment());
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
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorBlue_009CB2));
    }

    @Override
    public void doSomething() {
        pushFragment(new FriendFragment());
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

    private void slideHeightAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    private void pushFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.flMainDoctorMainActivity.getId(), fragment);
        // transaction.addToBackStack(null);
        transaction.commit();
    }
}
