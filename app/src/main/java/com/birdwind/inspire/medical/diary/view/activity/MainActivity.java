package com.birdwind.inspire.medical.diary.view.activity;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentHistory;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentNavigationListener;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityMainBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.service.InspireDiaryChatService;
import com.birdwind.inspire.medical.diary.view.fragment.ChatFragment;
import com.birdwind.inspire.medical.diary.view.fragment.PatientFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.viewCallback.BottomNavigationCallback;
import com.leaf.library.StatusBarUtil;
import com.tbruyelle.rxpermissions3.Permission;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AbstractActivity<AbstractPresenter, ActivityMainBinding>
    implements AbstractActivity.PermissionRequestListener, FragNavController.TransactionListener,
    FragNavController.RootFragmentListener, FragmentNavigationListener, View.OnClickListener {

    private boolean doubleBackToExitPressedOnce;

    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    // private FragNavTransactionOptions tabToRightFragNavTransactionOptions;
    //
    // private FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    private BottomNavigationCallback bottomNavigationCallback;

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, App.userModel.getIdentityMainColor());
    }

    @Override
    public void doSomething() {

    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivityMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.compTopBarMainActivity.llBackTopBarComp.setOnClickListener(this);
        binding.compTopBarMainActivity.btRightButtonTopBarComp.setOnClickListener(this);
        binding.compTopBarMainActivity.llRightButtonTopBarComp.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        doubleBackToExitPressedOnce = false;

        FragNavTransactionOptions defaultFragNavTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();
        //
        // tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
        // .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();
        //
        // tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
        // .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        fragmentHistory = new FragmentHistory();

        mNavController =
            FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), binding.mainContainer.getId())
                .transactionListener(this).rootFragmentListener(this, 1)
                .defaultTransactionOptions(defaultFragNavTransactionOptions).build();
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            showToast(getString(R.string.common_double_click_back));
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {
            if (permission.name.equals(Manifest.permission.CAMERA)) {
                pushFragment(new ScanFragment());
            }
        }
    }

    private void startSignalRService() {
        if (!SystemUtils.isServiceRunning(InspireDiaryChatService.class, context)) {
            Intent intent = new Intent(this, InspireDiaryChatService.class);
            this.startService(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startSignalRService();
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {}

    @Override
    public Fragment getRootFragment(int index) {
        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                return new PatientFragment();
            case PAINTER:
            case FAMILY:
                if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
                    return new QRCodeFragment();
                } else {
                    return new ChatFragment();
                }
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void popIndexTabFragment(int tab) {

    }

    @Override
    public void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode,
        boolean isShowBack, boolean isShowHeader, boolean isShowRightButton, String rightButtonText,
        int rightImageButton, BottomNavigationCallback bottomNavigationCallback) {
        if (title != null) {
            binding.compTopBarMainActivity.tvTitleTopBarComp.setText(title);
        }
        if (isStatusLightMode) {
            StatusBarUtil.setLightMode(this);
        } else {
            StatusBarUtil.setDarkMode(this);
        }
        binding.compTopBarMainActivity.tvTitleTopBarComp.setTextColor(ContextCompat.getColor(this, titleColor));
        binding.compTopBarMainActivity.ivBackTopBarComp.setColorFilter(ContextCompat.getColor(this, titleColor),
            android.graphics.PorterDuff.Mode.SRC_IN);

        if (backgroundColor == -1) {
            binding.compTopBarMainActivity.rlBackgroundTopBarComp
                .setBackgroundColor(ContextCompat.getColor(this, App.userModel.getIdentityMainColorId()));
        } else {
            binding.compTopBarMainActivity.rlBackgroundTopBarComp
                .setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        }

        if (isShowBack) {
            binding.compTopBarMainActivity.llBackTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarMainActivity.llBackTopBarComp.setVisibility(View.INVISIBLE);
        }

        if (isShowHeader) {
            binding.compTopBarMainActivity.rlBackgroundTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarMainActivity.rlBackgroundTopBarComp.setVisibility(View.GONE);
        }

        if (isShowRightButton) {
            binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.GONE);
        }

        binding.compTopBarMainActivity.btRightButtonTopBarComp.setText(rightButtonText);

        if (rightImageButton != 0) {
            binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.VISIBLE);
            binding.compTopBarMainActivity.ivRightButtonTopBarComp
                .setImageDrawable(ContextCompat.getDrawable(context, rightImageButton));
        } else {
            binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.GONE);
        }

        this.bottomNavigationCallback = bottomNavigationCallback;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.compTopBarMainActivity.llBackTopBarComp) {
            onBackPressed();
        } else if (v == binding.compTopBarMainActivity.btRightButtonTopBarComp) {
            if (bottomNavigationCallback != null) {
                bottomNavigationCallback.clickTopBarRightButton(v);
            }
        } else if (v == binding.compTopBarMainActivity.llRightButtonTopBarComp) {
            if (bottomNavigationCallback != null) {
                bottomNavigationCallback.clickTopBarRightImageButton(v);
            }
        }
    }

    public void openScanFragment() {
        if (!hasPermission(Manifest.permission.CAMERA)) {
            getPermission(new String[] {Manifest.permission.CAMERA}, this);
        } else {
            pushFragment(new ScanFragment());
        }
    }
}
