package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentSettingBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.bumptech.glide.Glide;

public class SettingFragment extends AbstractFragment<AbstractPresenter, FragmentSettingBinding> {

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentSettingBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ibEditAvatarSettingFragment.setOnClickListener(v -> {
            showToast(getString(R.string.function_not_complete));
        });

        binding.tvLogoutSettingFragment.setOnClickListener(v -> {
            ((MainActivity) context).logout();
        });
    }

    @Override
    public void initView() {
        binding.tvNameSettingFragment.setText(App.userModel.getName());

        Glide.with(context).load(App.userModel.getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar))
            .into(binding.civAvatarSettingFragment);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}
}
