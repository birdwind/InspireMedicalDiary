package com.birdwind.inspire.medical.diary.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentSettingBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;
import com.birdwind.inspire.medical.diary.presenter.SettingPresenter;
import com.birdwind.inspire.medical.diary.utils.EasyImageUtils;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.SettingView;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions3.Permission;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class SettingFragment extends AbstractFragment<SettingPresenter, FragmentSettingBinding>
    implements SettingView, ProgressRequestBody.UploadCallbacks, AbstractActivity.PermissionRequestListener {

    private EasyImageUtils easyImageUtils;

    private EasyImage easyImage;

    protected SlideHeightAnimation expandSlideMemberGroupAnimation;

    protected SlideHeightAnimation shrinkSlideMemberGroupAnimation;

    protected RotateAnimation expandRotateArrowAnimation;

    protected RotateAnimation shrinkRotateArrowAnimation;

    private boolean isShowPatientInfo;

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public FragmentSettingBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentSettingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ibEditAvatarSettingFragment.setOnClickListener(v -> {
            if (hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                easyImageUtils.showEasyImage(this);
            } else {
                getPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        // binding.ibPatientEditAvatarSettingFragment.setOnClickListener(v -> {
        // if (hasPermission(Manifest.permission.CAMERA,
        // Manifest.permission.WRITE_EXTERNAL_STORAGE,
        // Manifest.permission.READ_EXTERNAL_STORAGE)) {
        // easyImageUtils.showEasyImage(this);
        // } else {
        // getPermission(this, Manifest.permission.CAMERA,
        // Manifest.permission.WRITE_EXTERNAL_STORAGE,
        // Manifest.permission.READ_EXTERNAL_STORAGE);
        // }
        // });

        binding.tvBasicInfoSettingFragment.setOnClickListener(v -> {
            BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("identity", App.userModel.getIdentityEnums());
            basicInfoFragment.setArguments(bundle);
            pushFragment(basicInfoFragment);
        });

//        binding.llPatientSettingFragment.setOnClickListener(v -> {
//            hidePatientInfo(!isShowPatientInfo);
//        });

        binding.tvLogoutSettingFragment.setOnClickListener(v -> {
            ((MainActivity) context).logout();
        });

        binding.llPatientBasicInfoSettingFragment.setOnClickListener(v->{
            BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("identity", IdentityEnums.PAINTER);
            basicInfoFragment.setArguments(bundle);
            pushFragment(basicInfoFragment);
        });
    }

    @Override
    public void initView() {
        binding.tvNameSettingFragment.setText(App.userModel.getName());

        loadAvatar();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowPatientInfo = false;
        easyImageUtils = new EasyImageUtils(context, getString(R.string.setting_basic_upload_avatar));
        easyImage = easyImageUtils.getEasyImage();

        if(App.userModel.isProxy()){
            binding.rlPatientSettingFragment.setVisibility(View.VISIBLE);
        }

        expandSlideMemberGroupAnimation = new SlideHeightAnimation(binding.ibDropdownPatientSettingFragment,
            Utils.dp2px(context, 45), Utils.dp2px(context, 148), 300);
        shrinkSlideMemberGroupAnimation = new SlideHeightAnimation(binding.ibDropdownPatientSettingFragment,
            Utils.dp2px(context, 148), Utils.dp2px(context, 45), 300);
        expandSlideMemberGroupAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideMemberGroupAnimation.setInterpolator(new AccelerateInterpolator());

        expandRotateArrowAnimation =
            new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrinkRotateArrowAnimation =
            new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    }

    @Override
    public void doSomething() {}

    private void hidePatientInfo(boolean isHide) {
        isShowPatientInfo = isHide;
        if (isHide) {
            startAnimation(binding.rlPatientSettingFragment, shrinkSlideMemberGroupAnimation);
            startAnimation(binding.ibDropdownPatientSettingFragment, shrinkRotateArrowAnimation);
        } else {
            startAnimation(binding.rlPatientSettingFragment, expandSlideMemberGroupAnimation);
            startAnimation(binding.ibDropdownPatientSettingFragment, expandRotateArrowAnimation);
        }
    }

    private void startAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    @Override
    public void onUpdateAvatar(boolean isSuccess, UploadMediaResponse.Response uploadMediaResponse) {
        if (isSuccess) {
            App.userModel.setPhotoUrl(uploadMediaResponse.getMediaLink());
            App.updateUserModel();
            loadAvatar();
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("上傳", String.valueOf(percentage));
    }

    @Override
    public void onError() {
        LogUtils.d("上傳", "失敗");
        hideLoading();
    }

    @Override
    public void onFinish() {
        LogUtils.d("上傳", "完成");
        hideLoading();
    }

    private void loadAvatar() {
        Glide.with(context).load(App.userModel.getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar))
            .into(binding.civAvatarSettingFragment);
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {
            easyImageUtils.showEasyImage(this);
        } else if (permission.shouldShowRequestPermissionRationale) {
            showToast(getString(R.string.error_common_permission_denied_some));
        } else {
            showToast(getString(R.string.error_common_permission_never_show));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uploadAvatar(new File(result.getUri().getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                showToast(getString(R.string.error_common_unknow));
                LogUtils.exception(error);
            }
        }

        if (resultCode == RESULT_OK) {
            easyImage.handleActivityResult(requestCode, resultCode, data, (AbstractActivity) context,
                new EasyImage.Callbacks() {
                    @Override
                    public void onImagePickerError(@NonNull Throwable throwable, @NonNull MediaSource mediaSource) {
                        showToast(getString(R.string.error_common_unknow));
                        LogUtils.exception(throwable);
                    }

                    @Override
                    public void onMediaFilesPicked(@NonNull MediaFile[] mediaFiles, @NonNull MediaSource mediaSource) {
                        cropImage(mediaFiles[0].getFile());
                    }

                    @Override
                    public void onCanceled(@NonNull MediaSource mediaSource) {

                    }
                });
        }
    }

    private void cropImage(File file) {
        CropImage.activity(Uri.fromFile(file)).start(context, this);
    }

    private void uploadAvatar(File file) {
        presenter.uploadAvatar(file, this);
    }
}
