package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQrcodeBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.server.FileApiServer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

public class QRCodeFragment extends AbstractFragment<AbstractPresenter, FragmentQrcodeBinding> {

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
//        GlideUrl glideUrl = new GlideUrl(Config.BASE_URL + FileApiServer.MQ_QR_CODE.valueOfName(),
//            new LazyHeaders.Builder().addHeader("OS", "A").addHeader("Ver", "Config.APP_VERSION")
//                .addHeader("Token",
//                    App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() : "0000")
//                .build());
        Glide.with(this).load(Config.BASE_URL + FileApiServer.MQ_QR_CODE.valueOfName()).into(binding.ivQrcodeFragment);
    }
}
