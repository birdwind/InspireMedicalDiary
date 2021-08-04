package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityAuthBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class AuthActivity extends AbstractActivity<AbstractPresenter, ActivityAuthBinding> {

    public static final String LOGIN_TYPE = "LOGIN_TYPE";

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityAuthBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.flDoctorAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.DOCTOR);
        });
        binding.flPatientAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.PAINTER);
        });
        binding.flFamilyMembersAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.FAMILY);
        });
        binding.btDoctorAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.DOCTOR);
        });
        binding.btPatientAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.PAINTER);
        });
        binding.btFamilyMembersAuthActivity.setOnClickListener(v -> {
            openLoginActivity(IdentityEnums.FAMILY);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {

    }

    private void openLoginActivity(IdentityEnums identityEnums) {
        Bundle bundle = new Bundle();
        bundle.putInt(LOGIN_TYPE, identityEnums.getType());
        startActivityWithFinish(LoginActivity.class, bundle);
    }
}
