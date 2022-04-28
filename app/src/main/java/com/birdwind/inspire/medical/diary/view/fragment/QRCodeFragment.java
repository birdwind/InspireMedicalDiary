package com.birdwind.inspire.medical.diary.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQrcodeBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.CheckDiseaseResponse;
import com.birdwind.inspire.medical.diary.presenter.QRCordPresenter;
import com.birdwind.inspire.medical.diary.server.FileApiServer;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.QRCodeView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class QRCodeFragment extends AbstractFragment<QRCordPresenter, FragmentQrcodeBinding>
    implements QRCodeView, ToolbarCallback {

    @Override
    public QRCordPresenter createPresenter() {
        return new QRCordPresenter(this);
    }

    @Override
    public FragmentQrcodeBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentQrcodeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ivReloadQrcodeFragment.setOnClickListener(v -> {
            loadQRCode();
        });
        binding.rlMainQrcodeFragment.setOnClickListener(v -> {
            presenter.checkDisease();
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {
        loadQRCode();
    }

    @Override
    public String setRightButtonText() {
        if ((App.userModel.getIdentityEnums() == IdentityEnums.PAINTER
            && App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET)
            || (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily())) {
            return getString(R.string.common_logout);
        } else if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && App.userModel.isHasFamily()) {
            return getString(R.string.scan_be_family);
        } else {
            return "";
        }
    }

    @Override
    public String setLeftButtonText() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily()) {
            return getString(R.string.scan_be_family);
        } else {
            return "";
        }
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public boolean isShowTopBarBack() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.PAINTER
            && App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void clickTopBarRightTextButton(View view) {
        if ((App.userModel.getIdentityEnums() == IdentityEnums.PAINTER
            && App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET)
            || (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && !App.userModel.isHasFamily())) {
            // 登出
            showDialog(getString(R.string.common_dialog_title), getString(R.string.common_dialog_logout),
                new CommonDialogListener() {
                    @Override
                    public void clickConfirm() {
                        ((MainActivity) context).logout();
                    }
                });
        } else if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && App.userModel.isHasFamily()) {
            // 家屬
            ((MainActivity) context).replaceFragment(new ScanFragment(), true);
        }
    }

    @Override
    public void clickTopBarLeftTextButton(View view) {
        ((MainActivity) context).replaceFragment(new ScanFragment(), true);
    }

    private void loadQRCode() {
         CustomPicasso.getImageLoader(context).load(Config.BASE_URL + FileApiServer.MY_QRCODE.valueOfName())
         .into(binding.ivQrcodeFragment);
    }

    @Override
    public void checkDisease(boolean isSuccess, CheckDiseaseResponse.Response response) {
        if (isSuccess) {
            App.userModel.setDiseaseEnums(DiseaseEnums.parseEnumsByType(response.getDisease()));
            App.userModel.setNeedPatineName(response.isNeedSetPatientName());
            App.updateUserModel();
            if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
                App.userModel.setProxy(true);
                App.updateUserModel();
                ((MainActivity) context).replaceFragment(new FamilyMainFragment(), false);
            } else {
                ((MainActivity) context).replaceFragment(new PatientMainFragment(), false);
            }
        }
    }
}
