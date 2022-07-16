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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentSettingBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.enums.SettingFunctionEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.UploadMediaResponse;
import com.birdwind.inspire.medical.diary.presenter.SettingPresenter;
import com.birdwind.inspire.medical.diary.utils.EasyImageUtils;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.activity.WebViewActivity;
import com.birdwind.inspire.medical.diary.view.adapter.SettingItemAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.SettingView;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions3.Permission;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class SettingFragment extends AbstractFragment<SettingPresenter, FragmentSettingBinding>
    implements SettingView, ProgressRequestBody.UploadCallbacks, AbstractActivity.PermissionRequestListener {

    private EasyImageUtils easyImageUtils;

    private EasyImage easyImage;

    private boolean isShowPatientInfo;

    private SettingItemAdapter patientSettingItemAdapter;

    private SettingItemAdapter settingItemAdapter;

    private List<SettingFunctionEnums> patientSettingList;

    private List<SettingFunctionEnums> settingList;

    private boolean isUpdatePatientPhoto;

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
                isUpdatePatientPhoto = false;
                easyImageUtils.showEasyImage(this);
            } else {
                getPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        binding.ibPatinetEditAvatarSettingFragment.setOnClickListener(v -> {
            if (hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                isUpdatePatientPhoto = true;
                easyImageUtils.showEasyImage(this);
            } else {
                getPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.llPatientBasicInfoSettingFragment.setOnClickListener(v -> {
            settingAdapterOnClick(SettingFunctionEnums.PERSONAL_INFO);
        });

        settingItemAdapter.setOnItemClickListener((adapter, view, position) -> {
            SettingFunctionEnums settingFunctionEnums = (SettingFunctionEnums) adapter.getItem(position);
            settingAdapterOnClick(settingFunctionEnums);
        });

        patientSettingItemAdapter.setOnItemClickListener((adapter, view, position) -> {
            SettingFunctionEnums settingFunctionEnums = (SettingFunctionEnums) adapter.getItem(position);
            settingAdapterOnClick(settingFunctionEnums, IdentityEnums.PAINTER);
        });
    }

    @Override
    public void initView() {
        binding.tvNameSettingFragment.setText(App.userModel.getName());

        setProxyInfo();

        binding.rvPatientListSettingFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvPatientListSettingFragment.setHasFixedSize(true);
        binding.rvPatientListSettingFragment.setAdapter(patientSettingItemAdapter);

        binding.rvListSettingFragment
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvListSettingFragment.setHasFixedSize(true);
        binding.rvListSettingFragment.setAdapter(settingItemAdapter);

        loadAvatar();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowPatientInfo = false;
        isUpdatePatientPhoto = false;
        easyImageUtils = new EasyImageUtils(context, getString(R.string.setting_basic_upload_avatar));
        easyImage = easyImageUtils.getEasyImage();

        patientSettingItemAdapter = new SettingItemAdapter(R.layout.item_setting);
        patientSettingItemAdapter.setAnimationEnable(true);
        settingItemAdapter = new SettingItemAdapter(R.layout.item_setting);
        settingItemAdapter.setAnimationEnable(true);
        patientSettingList = new ArrayList<>();
        settingList = new ArrayList<>();

        patientSettingList.add(SettingFunctionEnums.PERSONAL_INFO);

        settingList.add(SettingFunctionEnums.PERSONAL_INFO);
        settingList.add(SettingFunctionEnums.FEEDBACK);
        settingList.add(SettingFunctionEnums.LOGOUT);
        settingList.add(SettingFunctionEnums.TEMP);

        patientSettingItemAdapter.setList(patientSettingList);
        settingItemAdapter.setList(settingList);
    }

    @Override
    public void doSomething() {
        patientSettingItemAdapter.setRecyclerView(binding.rvPatientListSettingFragment);
        settingItemAdapter.setRecyclerView(binding.rvListSettingFragment);

        if (App.userModel.isProxy()) {
            presenter.getBasicInfo(App.userModel.getUid(), IdentityEnums.PAINTER);
        }
    }

    @Override
    public void onGetInformation(boolean isSuccess, InformationResponse.Response response) {
        App.userModel.setProxyPatientName(response.getName());
        App.userModel.setProxyPatientPhotoUrl(response.getPhotoUrl());
        App.updateUserModel();
        setProxyInfo();
        loadAvatar();
    }

    @Override
    public void onUpdateAvatar(boolean isSuccess, UploadMediaResponse.Response uploadMediaResponse,
        IdentityEnums identityEnums) {
        if (isSuccess) {
            if (App.userModel.isProxy() && identityEnums == IdentityEnums.PAINTER) {
                App.userModel.setProxyPatientPhotoUrl(uploadMediaResponse.getMediaLink());
            } else {
                App.userModel.setPhotoUrl(uploadMediaResponse.getMediaLink());
            }
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
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar))
            .into(binding.civAvatarSettingFragment);
        if (App.userModel.isProxy()) {
            Glide.with(context).load(App.userModel.getProxyPatientPhotoUrl())
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar))
                .into(binding.civPatientAvatarSettingFragment);
        }
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
                uploadAvatar(new File(result.getUri().getPath()), isUpdatePatientPhoto ? IdentityEnums.PAINTER : null);
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

    private void uploadAvatar(File file, IdentityEnums identityEnums) {
        presenter.uploadAvatar(file, this, identityEnums);
    }

    private void settingAdapterOnClick(SettingFunctionEnums settingFunctionEnums) {
        settingAdapterOnClick(settingFunctionEnums, App.userModel.getIdentityEnums());
    }

    private void settingAdapterOnClick(SettingFunctionEnums settingFunctionEnums, IdentityEnums identityEnums) {
        Bundle bundle;
        switch (settingFunctionEnums) {
            case PERSONAL_INFO:
                bundle = new Bundle();
                BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
                bundle.putSerializable("identity", identityEnums);
                basicInfoFragment.setArguments(bundle);
                pushFragment(basicInfoFragment);
                break;
            case LOGOUT:
                ((MainActivity) context).logout();
                break;
            case FEEDBACK:
                bundle = new Bundle();
                bundle.putString("link", "https://coda.io/form/Quick-feedback-for-IM_d8PqQqYioZe");
                startActivity(WebViewActivity.class, bundle);
                break;
            case TEMP:
                App.isStepByStep = !App.isStepByStep;
                showToast("Changed Survey Type!");
                break;
        }
    }

    private void setProxyInfo() {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY && App.userModel.isProxy()) {
            binding.tvIdentitySettingFragment.setVisibility(View.VISIBLE);
            binding.llPatientSettingFragment.setVisibility(View.VISIBLE);
            binding.tvPatientNameSettingFragment.setText(App.userModel.getProxyPatientName());
            binding.tvPatientDiseaseSettingFragment
                .setText(getString(App.userModel.getDiseaseEnums().getDiseaseNameId()));
        }
    }
}
