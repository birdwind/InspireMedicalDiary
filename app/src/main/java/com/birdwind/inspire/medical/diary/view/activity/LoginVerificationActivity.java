package com.birdwind.inspire.medical.diary.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.content.res.AppCompatResources;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityLoginVerificationBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.LoginVerificationPresenter;
import com.birdwind.inspire.medical.diary.utils.CountDownUtils;
import com.birdwind.inspire.medical.diary.view.viewCallback.LoginVerificationView;
import com.leaf.library.StatusBarUtil;

public class LoginVerificationActivity
    extends AbstractActivity<LoginVerificationPresenter, ActivityLoginVerificationBinding>
    implements LoginVerificationView, CountDownUtils.CountDownUtilsCallback {

    private Bundle bundle;

    private String phone;

    private String name;

    private IdentityEnums identityEnums;

    private String FCM;

    private CountDownUtils countDownUtils;

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
            if (!countDownUtils.isCountDown()) {
                presenter.sendVerificationCode(phone, FCM);
            }
        });

        binding.btVerifyLoginVerificationActivity.setOnClickListener(v -> {
            verify();
        });

        binding.etVerificationCodeLoginVerificationActivity.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                verify();
            }
            return false;
        });

        binding.llBackLoginVerificationActivity.setOnClickListener(v -> {
            onBackPressed();
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
        countDownUtils = new CountDownUtils(60, this);
    }

    @Override
    public void initView() {
        StatusBarUtil.setDarkMode(this);
        binding.etNameLoginVerificationActivity.setText(name);
    }

    @Override
    public void doSomething() {
        initVerifyButtonBackground();
        presenter.sendVerificationCode(phone, FCM);
        countDownUtils.start();
    }

    @Override
    public void onBackPressed() {
        bundle.putString("phone", phone);
        bundle.putString("name", binding.etNameLoginVerificationActivity.getText().toString());
        bundle.putInt(AuthActivity.LOGIN_TYPE, identityEnums.getType());
        startActivityWithFinish(LoginActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        countDownUtils.delete();
        super.onDestroy();
    }

    @Override
    public void onSendVerify(boolean isSuccess) {
        if (isSuccess) {
            countDownUtils.start();
        }
    }

    @Override
    public void onVerify(boolean isSuccess) {
        App.userModel.setIdentityEnums(identityEnums);
        startActivityWithFinish(MainActivity.class);
        App.updateUserModel();
    }

    private void initVerifyButtonBackground() {
        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.bg_double_green);
        switch (identityEnums) {
            case DOCTOR:
                drawable = AppCompatResources.getDrawable(this, R.drawable.bg_double_green);
                break;
            case FAMILY:
                drawable = AppCompatResources.getDrawable(this, R.drawable.bg_double_red);
                break;
            case PAINTER:
                drawable = AppCompatResources.getDrawable(this, R.drawable.bg_double_orange);
                break;
        }

        binding.btVerifyLoginVerificationActivity.setBackground(drawable);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long sec = (millisUntilFinished / 1000) % 60;
        binding.btSendVerificationCodeLoginVerificationActivity.setText(
            getString(R.string.login_send_verification_code_countdown).replace("${second}", String.valueOf(sec)));
    }

    @Override
    public void onFinish() {
        binding.btSendVerificationCodeLoginVerificationActivity
            .setText(getString(R.string.login_send_verification_code));
    }

    private void verify() {
        name = binding.etNameLoginVerificationActivity.getText().toString();
        String verificationCode = binding.etVerificationCodeLoginVerificationActivity.getText().toString();
        presenter.verify(phone, verificationCode, name, FCM, identityEnums);
    }
}
