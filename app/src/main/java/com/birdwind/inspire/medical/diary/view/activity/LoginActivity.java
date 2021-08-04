package com.birdwind.inspire.medical.diary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityLoginBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.request.LoginRequest;
import com.birdwind.inspire.medical.diary.presenter.LoginPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.LoginView;
import com.leaf.library.StatusBarUtil;

public class LoginActivity extends AbstractActivity<LoginPresenter, ActivityLoginBinding> implements LoginView {

    private String phone;
    private String name;
    private IdentityEnums identityEnums;

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public ActivityLoginBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btLoginLoginActivity.setOnClickListener(v -> {
            login();
        });

        binding.etPhoneLoginActivity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
            }
            return false;
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int identity = 0;
        if (bundle != null) {
            phone = bundle.getString("phone", "");
            name = bundle.getString("name", "");
            identity = bundle.getInt(AuthActivity.LOGIN_TYPE, IdentityEnums.PAINTER.getType());
        } else {
            phone = "";
            name = "";
        }
        identityEnums = IdentityEnums.parseEnumsByType(identity);
    }

    @Override
    public void initView() {
        StatusBarUtil.setDarkMode(this);
        binding.etPhoneLoginActivity.setText(phone);
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onBackPressed() {
        startActivityWithFinish(AuthActivity.class);
    }

    @Override
    public void onLoginSuccess() {
        startActivityWithFinish(DoctorMainActivity.class);
    }

    @Override
    public void onLoginNeedVerify() {
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        bundle.putString("name", name);
        bundle.putInt(AuthActivity.LOGIN_TYPE, identityEnums.getType());
        startActivityWithFinish(LoginVerificationActivity.class, bundle);
    }

    private void login() {
        phone = binding.etPhoneLoginActivity.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("請輸入正確的手機");
            return;
        }
        LoginRequest loginRequest = new LoginRequest(phone, identityEnums);
        presenter.login(loginRequest);
    }
}
