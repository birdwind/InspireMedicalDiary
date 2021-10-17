package com.birdwind.inspire.medical.diary.view.fragment;

import android.Manifest;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentScanBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.ScanUserModel;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.presenter.ScanPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.dialog.UserDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.dialog.callback.UserDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScanView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.tbruyelle.rxpermissions3.Permission;

public class ScanFragment extends AbstractFragment<ScanPresenter, FragmentScanBinding>
    implements ScanView, AbstractActivity.PermissionRequestListener, QRCodeReaderView.OnQRCodeReadListener,
    UserDialogListener, ToolbarCallback {

    private UserDialog userDialog;

    private boolean isShowDialog;

    private boolean isAdded;

    @Override
    public ScanPresenter createPresenter() {
        return new ScanPresenter(this);
    }

    @Override
    public FragmentScanBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentScanBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.qrcodeCameraScanFragment.setOnQRCodeReadListener(this);
        binding.tvPermissionScanFragment.setOnClickListener(v -> {
            if (!hasPermission(Manifest.permission.CAMERA)) {
                getPermission(new String[] {Manifest.permission.CAMERA}, this);
            }
        });
    }

    @Override
    public void initView() {
        binding.qrcodeCameraScanFragment.setQRDecodingEnabled(true);
        binding.qrcodeCameraScanFragment.setTorchEnabled(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowDialog = false;
        isAdded = false;
    }

    @Override
    public void doSomething() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            binding.tvPermissionScanFragment.setVisibility(View.GONE);
            binding.qrcodeCameraScanFragment.setVisibility(View.VISIBLE);
        } else {
            binding.tvPermissionScanFragment.setVisibility(View.VISIBLE);
            binding.qrcodeCameraScanFragment.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            binding.qrcodeCameraScanFragment.startCamera();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.qrcodeCameraScanFragment.stopCamera();
        if (userDialog != null && userDialog.isShowing()) {
            userDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (!isShowDialog) {
            isShowDialog = true;
            ScanUserModel scanUserModel = GsonUtils.parseJsonToBean(text, ScanUserModel.class);
            binding.qrcodeCameraScanFragment.stopCamera();
            presenter.checkQRCodeUid(scanUserModel.getUID());
        }
    }

    @Override
    public void onCheckUidResponse(boolean isSuccess, UserResponse userResponse) {
        if (isSuccess) {
            userDialog = new UserDialog(context, userResponse, this);
            userDialog.show();
        } else {
            binding.qrcodeCameraScanFragment.startCamera();
        }
    }

    @Override
    public void userDialogAdded() {
        isAdded = true;
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
            ((MainActivity) context).replaceFragment(new FamilyMainFragment(), false);
            App.userModel.setHasFamily(true);
            App.updateUserModel();
        } else {
            onBackPressedByActivity();
        }
    }

    @Override
    public void userDialogClose() {
        if (!isAdded) {
            isShowDialog = false;
            binding.qrcodeCameraScanFragment.startCamera();
            binding.qrcodeCameraScanFragment.forceAutoFocus();
        } else {
            onBackPressedByActivity();
        }
    }

    @Override
    public String setRightButtonText() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily()) {
            return getString(R.string.common_logout);
        } else if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && App.userModel.isHasFamily()) {
            return getString(R.string.scan_be_agent);
        } else {
            return "";
        }
    }

    @Override
    public String setLeftButtonText() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily()) {
            return getString(R.string.scan_be_agent);
        } else {
            return "";
        }
    }

    @Override
    public boolean isShowTopBarBack() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void clickTopBarRightTextButton(View view) {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && App.userModel.isHasFamily()) {
            // 代理人
            ((MainActivity) context).replaceFragment(new QRCodeFragment(), false);
        } else {
            // 登出
            showDialog(getString(R.string.common_dialog_title), getString(R.string.common_dialog_logout),
                new CommonDialogListener() {
                    @Override
                    public void clickConfirm() {
                        ((MainActivity) context).logout();
                    }
                });
        }
    }

    @Override
    public void clickTopBarLeftTextButton(View view) {
        ((MainActivity) context).replaceFragment(new QRCodeFragment(), false);
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.name.equals(Manifest.permission.CAMERA)) {
            if (permission.granted) {
                binding.qrcodeCameraScanFragment.setVisibility(View.VISIBLE);
                binding.qrcodeCameraScanFragment.startCamera();
            } else if (permission.shouldShowRequestPermissionRationale) {
                showToast(getString(R.string.scan_no_permission));
            } else {
                showToast(getString(R.string.error_common_permission_never_show));
            }
        }
    }
}
