package com.birdwind.inspire.medical.diary.view.activity;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentHistory;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentNavigationListener;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.BackHandlerHelper;
import com.birdwind.inspire.medical.diary.databinding.ActivityMainBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.model.PainterDiseaseModel;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.receiver.PainterBroadcastReceiver;
import com.birdwind.inspire.medical.diary.service.InspireDiaryChatService;
import com.birdwind.inspire.medical.diary.view.fragment.DoctorMainFragment;
import com.birdwind.inspire.medical.diary.view.fragment.FamilyMainFragment;
import com.birdwind.inspire.medical.diary.view.fragment.PatientMainFragment;
import com.birdwind.inspire.medical.diary.view.fragment.QRCodeFragment;
import com.birdwind.inspire.medical.diary.view.fragment.ScanFragment;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.leaf.library.StatusBarUtil;
import com.tbruyelle.rxpermissions3.Permission;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    private ToolbarCallback toolbarCallback;

    private PainterBroadcastReceiver painterBroadcastReceiver;

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

        painterBroadcastReceiver = new PainterBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                PainterDiseaseModel painterDiseaseModel =
                    (PainterDiseaseModel) bundle.getSerializable("painterDiseaseModel");
                App.userModel.setDiseaseEnums(DiseaseEnums.parseEnumsByType(painterDiseaseModel.getDisease()));
                App.updateUserModel();
                replaceFragment(new PatientMainFragment());
            }
        };
        painterBroadcastReceiver.register(context);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if (!mNavController.isRootFragment()) {
                mNavController.popFragment(popFragNavTransactionOptions);
            } else {
                if (App.isDoubleBack()) {
                    super.onBackPressed();
                } else {
                    showToast(getString(R.string.common_double_click_back));
                }
            }
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
                return new DoctorMainFragment();
            case PAINTER:
                if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
                    return new QRCodeFragment();
                } else {
                    return new PatientMainFragment();
                }
            case FAMILY:
                // TODO:定義加入群組資料格式
                if (App.userModel.isHasFamily()) {
                    return new FamilyMainFragment();
                } else {
                    return new ScanFragment();
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
    public void popIndexTabFragment(int tab) {}

    @Override
    public void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode,
        boolean isShowBack, boolean isShowHeader, boolean isShowRightButton, String rightButtonText,
        int rightImageButton, ToolbarCallback toolbarCallback) {
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

        if (!TextUtils.isEmpty(rightButtonText)) {
            binding.compTopBarMainActivity.btRightButtonTopBarComp.setText(rightButtonText);
            binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.VISIBLE);
        } else {
            binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.GONE);
        }

        if (rightImageButton != 0) {
            binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.VISIBLE);
            binding.compTopBarMainActivity.ivRightButtonTopBarComp
                .setImageDrawable(ContextCompat.getDrawable(context, rightImageButton));
        } else {
            binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.GONE);
        }

        this.toolbarCallback = toolbarCallback;
    }

    @Override
    public void onClick(View v) {
        if (v == binding.compTopBarMainActivity.llBackTopBarComp) {
            onBackPressed();
        } else if (v == binding.compTopBarMainActivity.btRightButtonTopBarComp) {
            if (toolbarCallback != null) {
                toolbarCallback.clickTopBarRightTextButton(v);
            }
        } else if (v == binding.compTopBarMainActivity.llRightButtonTopBarComp) {
            if (toolbarCallback != null) {
                toolbarCallback.clickTopBarRightImageButton(v);
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

    public void replaceFragment(Fragment fragment) {
        mNavController.replaceFragment(fragment);
    }
}
