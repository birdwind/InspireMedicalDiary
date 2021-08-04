package com.birdwind.inspire.medical.diary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityLoginVerificationBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.LoginVerificationPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.LoginVerificationView;
import com.leaf.library.StatusBarUtil;

public class LoginVerificationActivity extends
    AbstractActivity<LoginVerificationPresenter, ActivityLoginVerificationBinding> implements LoginVerificationView {

    private Bundle bundle;

    private String phone;

    private String name;

    private IdentityEnums identityEnums;

    private String FCM;

    @Override
    public LoginVerificationPresenter createPresenter() {
        return new LoginVerificationPresenter(this);
    }

    @Override
    public ActivityLoginVerificationBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityLoginVerificationBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btSendVerificationCodeLoginVerificationActivity.setOnClickListener(v -> {
            presenter.sendVerificationCode(phone, FCM);
        });

        binding.btVerifyLoginVerificationActivity.setOnClickListener(v -> {
            name = binding.etNameLoginVerificationActivity.getText().toString();
            String verificationCode = binding.etVerificationCodeLoginVerificationActivity.getText().toString();
            presenter.verify(phone, verificationCode, name, FCM, identityEnums);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        int identity = 0;
        if (bundle != null) {
            phone = bundle.getString("phone", "");
            name = bundle.getString("name", "");
            identity = bundle.getInt(AuthActivity.LOGIN_TYPE, IdentityEnums.PAINTER.getType());
        } else {
            phone = "";
            name = "";
        }
        FCM = SystemUtils.initUniquePass();
        identityEnums = IdentityEnums.parseEnumsByType(identity);
    }

    @Override
    public void initView() {
        StatusBarUtil.setDarkMode(this);
        binding.etNameLoginVerificationActivity.setText(name);

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onBackPressed() {
        bundle.putString("phone", phone);
        bundle.putString("name", binding.etNameLoginVerificationActivity.getText().toString());
        bundle.putInt(AuthActivity.LOGIN_TYPE, identityEnums.getType());
        startActivityWithFinish(LoginActivity.class, bundle);
    }

    @Override
    public void onVerify(boolean isSuccess) {
        startActivityWithFinish(DoctorMainActivity.class);
    }
}
