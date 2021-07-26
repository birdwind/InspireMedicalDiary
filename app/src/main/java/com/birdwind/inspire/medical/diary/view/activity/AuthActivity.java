package com.birdwind.inspire.medical.diary.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityAuthBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class AuthActivity extends AbstractActivity<AbstractPresenter, ActivityAuthBinding> {

    public final String LOGIN_TYPE = "LOGIN_TYPE";

    public final int DOCTOR_LOGIN = 0;
    public final int PATIENT_LOGIN = 1;
    public final int FAMILY_MEMBER_LOGIN = 2;

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
            openLoginActivity(DOCTOR_LOGIN);
        });
        binding.flPatientAuthActivity.setOnClickListener(v -> {
            openLoginActivity(PATIENT_LOGIN);
        });
        binding.flFamilyMembersAuthActivity.setOnClickListener(v -> {
            openLoginActivity(FAMILY_MEMBER_LOGIN);
        });
        binding.btDoctorAuthActivity.setOnClickListener(v -> {
            openLoginActivity(DOCTOR_LOGIN);
        });
        binding.btPatientAuthActivity.setOnClickListener(v -> {
            openLoginActivity(PATIENT_LOGIN);
        });
        binding.btFamilyMembersAuthActivity.setOnClickListener(v -> {
            openLoginActivity(FAMILY_MEMBER_LOGIN);
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

    private void openLoginActivity(int loginType) {
        Bundle bundle = new Bundle();
        switch (loginType) {
            case DOCTOR_LOGIN:
                bundle.putInt(LOGIN_TYPE, DOCTOR_LOGIN);
                break;
            case PATIENT_LOGIN:
                bundle.putInt(LOGIN_TYPE, PATIENT_LOGIN);
                break;
            case FAMILY_MEMBER_LOGIN:
                bundle.putInt(LOGIN_TYPE, FAMILY_MEMBER_LOGIN);
                break;
        }
        startActivityWithFinish(LoginActivity.class);
    }
}
