package com.birdwind.inspire.medical.diary.view.fragment;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentScanBinding;
import com.birdwind.inspire.medical.diary.model.ScanUserModel;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.presenter.ScanPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.UserDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.UserDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScanView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class ScanFragment extends AbstractFragment<ScanPresenter, FragmentScanBinding> implements ScanView,
    AbstractActivity.PermissionRequestListener, QRCodeReaderView.OnQRCodeReadListener, UserDialogListener {

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
    public void doSomething() {}

    @Override
    public void onResume() {
        binding.qrcodeCameraScanFragment.startCamera();
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
        onBackPressed();
    }

    @Override
    public void userDialogClose() {
        if (!isAdded) {
            isShowDialog = false;
            binding.qrcodeCameraScanFragment.startCamera();
            binding.qrcodeCameraScanFragment.forceAutoFocus();
        } else {
            onBackPressed();
        }
    }
}
