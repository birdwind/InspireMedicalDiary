package com.birdwind.inspire.medical.diary.view.fragment;

import android.Manifest;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener;
import com.asynctaskcoffee.audiorecorder.worker.MediaPlayListener;
import com.asynctaskcoffee.audiorecorder.worker.Player;
import com.asynctaskcoffee.audiorecorder.worker.Recorder;
import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentRecordBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;
import com.birdwind.inspire.medical.diary.presenter.RecordPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.tbruyelle.rxpermissions3.Permission;

import java.io.File;

public class RecordFragment extends AbstractFragment<RecordPresenter, FragmentRecordBinding>
    implements RecordView, AbstractActivity.PermissionRequestListener, ToolbarCallback, AudioRecordListener,
    MediaPlayListener, ProgressRequestBody.UploadCallbacks {

    private Recorder recorder;

    private Player player;

    private boolean isRecording;

    private File file;

    private int id;

    @Override
    public RecordPresenter createPresenter() {
        return new RecordPresenter(this);
    }

    @Override
    public FragmentRecordBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentRecordBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ibRecordRecordFragment.setOnClickListener(v -> {
            if (!isRecording) {
                startRecord();
            }
        });

        binding.ibStopRecordRecordFragment.setOnClickListener(v -> {
            if (isRecording) {
                stopRecord();
            }
        });

        binding.ibPlayRecordFragment.setOnClickListener(v -> {
            if (player != null && player.getPlayer() != null) {
                if (player.getPlayer() != null) {
                    player.startPlaying();
                }
            }
        });

        binding.ibStopPlayRecordFragment.setOnClickListener(v -> {
            if (player != null && player.getPlayer() != null) {
                if (player.getPlayer().isPlaying()) {
                    player.stopPlaying();
                }
            }
        });

        binding.ibUploadRecordFragment.setOnClickListener(v -> {
            if (file != null && file.exists()) {
                presenter.uploadRecord(file, id, this);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isRecording = false;
    }

    @Override
    public void initView() {
        binding.ibPlayRecordFragment.setColorFilter(ContextCompat.getColor(context, R.color.colorGray_C4C4C4),
            PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void doSomething() {
        presenter.getVoiceQuiz();
        getPermission(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
            this);
    }

    private void startRecord() {
        recorder = new Recorder(this, context);
        recorder.setFileName("/" + App.userModel.getUid() + "_" + System.nanoTime() + ".m4a");
        binding.ibRecordRecordFragment.post(() -> {
            binding.ibRecordRecordFragment.setVisibility(View.GONE);
            binding.ibStopRecordRecordFragment.setVisibility(View.VISIBLE);
        });
        recorder.startRecord();
        isRecording = true;
    }

    private void stopRecord() {
        recorder.stopRecording();

        binding.ibRecordRecordFragment.post(() -> {
            binding.ibRecordRecordFragment.setVisibility(View.VISIBLE);
            binding.ibStopRecordRecordFragment.setVisibility(View.GONE);
        });
        isRecording = false;
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {

        } else if (permission.shouldShowRequestPermissionRationale) {
            ToastUtils.show(context, context.getString(R.string.error_common_permission_denied_some));
            onBackPressed();
        } else {
            ToastUtils.show(context, context.getString(R.string.error_common_permission_never_show));
            onBackPressed();
        }
    }

    @Override
    public void onAudioReady(@Nullable String audioUri) {
        LogUtils.d(audioUri);
        player = new Player(this);
        player.injectMedia(audioUri);
        file = new File(audioUri);
        binding.ibPlayRecordFragment.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack_000000),
            PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onReadyForRecord() {
        // READY FOR RECORD DO NOT CALL STOP RECORD BEFORE THIS CALLBACK
    }

    @Override
    public void onRecordFailed(@Nullable String errorMessage) {
        showToast(errorMessage);

        binding.ibRecordRecordFragment.post(() -> {
            binding.ibRecordRecordFragment.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack_000000),
                PorterDuff.Mode.SRC_IN);
        });
        isRecording = false;
    }

    @Override
    public void onStartMedia() {
        binding.ibPlayRecordFragment.setVisibility(View.GONE);
        binding.ibStopPlayRecordFragment.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopMedia() {
        binding.ibPlayRecordFragment.setVisibility(View.VISIBLE);
        binding.ibStopPlayRecordFragment.setVisibility(View.GONE);
    }

    @Override
    public void onGetVoiceQuiz(boolean isSuccess, VoiceQuizResponse.Response response) {
        CustomPicasso.getImageLoader(context).load(response.getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_default_image))
            .into(binding.ivImageRecordFragment);

        binding.tvDescriptionRecordFragment.setText(response.getContent());
        id = response.getVTID();
    }

    @Override
    public void onUploadRecord(boolean isSuccess) {

        if(getParentFragment() instanceof PatientMainFragment){
            ((PatientMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
        }else{
            ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
        }
//        if (App.userModel.getIdentityEnums() == IdentityEnums.PAINTER) {
//            ((PatientMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
//        } else if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
//            ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
//        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("上傳", String.valueOf(percentage));
//        if (progressLoadingDialog == null || !progressLoadingDialog.isShowing()) {
//            showLoadingFileDialog();
//        }
//        progressLoadingDialog.setPiePercentage(percentage);
    }

    @Override
    public void onError() {
        LogUtils.d("上傳", "失敗");
        hideFileDialog();
    }

    @Override
    public void onFinish() {
        LogUtils.d("上傳", "完成");
        hideFileDialog();
    }
}
