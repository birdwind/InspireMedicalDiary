package com.birdwind.inspire.medical.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractMainActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityPainterMainBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.model.PainterDiseaseModel;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.receiver.PainterBroadcastReceiver;
import com.birdwind.inspire.medical.diary.view.fragment.ChatFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QuizContentFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class PainterMainActivity extends AbstractMainActivity<AbstractPresenter, ActivityPainterMainBinding>
    implements AbstractActivity.PermissionRequestListener {

    private PainterBroadcastReceiver painterBroadcastReceiver;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityPainterMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return ActivityPainterMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.componentTopBarPainterMainActivity.llBackTopBarComp.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.llScanPainterMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_SCAN);

        });
        binding.llQrcodePainterMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_QRCODE);

        });
        binding.llQuizPainterMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_THREE);

        });
        binding.llVisitPainterMainActivity.setOnClickListener(v -> {
//            swipeFragent(PAGE_FOUR);

        });
        binding.llSettingPainterMainActivity.setOnClickListener(v -> {
//            swipeFragment(PAGE_FIVE);

        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        super.initData(savedInstanceState);
        painterBroadcastReceiver = new PainterBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                PainterDiseaseModel painterDiseaseModel = (PainterDiseaseModel) bundle.getSerializable("painterDiseaseModel");
                App.userModel.setDiseaseEnums(DiseaseEnums.parseEnumsByType(painterDiseaseModel.getDisease()));
                App.updateUserModel();
//                fragments = initFragmentList();
                binding.llMenuPainterMainActivity.setVisibility(View.VISIBLE);
//                swipeFragment(PAGE_DEFAULT, true);
            }
        };
        painterBroadcastReceiver.register(context);

    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void doSomething() {
        if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
            binding.llMenuPainterMainActivity.setVisibility(View.GONE);
        }
//        swipeFragment(PAGE_DEFAULT);
    }

//    @Override
//    protected View setTopBar() {
//        return binding.componentTopBarPainterMainActivity.rlBackgroundTopBarComp;
//    }
//
//    @Override
//    protected View setTopMenu() {
//        return binding.llMenuPainterMainActivity;
//    }
//
//    @Override
//    protected View setFragmentView() {
//        return binding.flMainPainterMainActivity;
//    }

    @Override
    protected List<Fragment> initFragmentList() {
        List<Fragment> currentFragmentList = new ArrayList<>();
        if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
            currentFragmentList.add(new QRCodeFragment());
        } else {
            currentFragmentList.add(new ChatFragment());
            currentFragmentList.add(new ScanFragment());
            currentFragmentList.add(new QRCodeFragment());
            currentFragmentList.add(new QuizContentFragment());
            currentFragmentList.add(new SettingFragment());
            currentFragmentList.add(new SettingFragment());
        }
        return currentFragmentList;
    }
}
