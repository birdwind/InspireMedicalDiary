package com.birdwind.inspire.medical.diary.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivitySplashBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.leaf.library.StatusBarUtil;

public class SplashActivity extends AbstractActivity<AbstractPresenter, ActivitySplashBinding> {
    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivitySplashBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    public void doSomething() {
        crossFade();
    }

    private void crossFade() {
        binding.llLogoSplashActivity.setAlpha(0f);
        binding.llLogoSplashActivity.setVisibility(View.VISIBLE);
        binding.llLogoSplashActivity.animate().alpha(1f).setDuration(3000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (App.userModel == null || TextUtils.isEmpty(App.userModel.getToken())) {
                    startActivityWithFinish(AuthActivity.class);
                } else {
                    startActivityWithFinish(MainActivity.class);
//                    switch (App.userModel.getIdentityEnums()) {
//                        case DOCTOR:
//                            startActivityWithFinish(DoctorMainActivity.class);
//                            break;
//                        case FAMILY:
//                            startActivityWithFinish(FamilyMainActivity.class);
//                            break;
//                        case PAINTER:
//                            startActivityWithFinish(PainterMainActivity.class);
//                            break;
//                    }
                }
            }
        });
    }
}
