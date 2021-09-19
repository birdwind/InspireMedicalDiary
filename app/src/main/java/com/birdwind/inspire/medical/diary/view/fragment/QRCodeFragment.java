package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQrcodeBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.server.FileApiServer;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.bumptech.glide.Glide;

public class QRCodeFragment extends AbstractFragment<AbstractPresenter, FragmentQrcodeBinding>
    implements ToolbarCallback {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentQrcodeBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentQrcodeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {
        Glide.with(this).load(Config.BASE_URL + FileApiServer.MQ_QR_CODE.valueOfName()).into(binding.ivQrcodeFragment);
    }

    @Override
    public String setRightButtonText() {
        return "登出";
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public boolean isShowTopBarBack() {
        if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void clickTopBarRightTextButton(View view) {
        showDialog("系統訊息", "您確定要登出嗎?", new CommonDialogListener() {
            @Override
            public void clickConfirm() {
                ((MainActivity) context).logout();
            }
        });
    }
}
