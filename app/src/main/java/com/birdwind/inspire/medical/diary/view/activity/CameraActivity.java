package com.birdwind.inspire.medical.diary.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityCameraBinding;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.presenter.CameraPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.CameraView;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;

public class CameraActivity extends AbstractActivity<CameraPresenter, ActivityCameraBinding>
    implements CameraView, CameraKitEventListener, ProgressRequestBody.UploadCallbacks {

    private boolean isStartRecord;

    private QuestionModel questionModel;

    @Override
    public CameraPresenter createPresenter() {
        return new CameraPresenter(this);
    }

    @Override
    public ActivityCameraBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivityCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ivStartCameraActivity.setOnClickListener(v -> {
            if (isStartRecord) {
                binding.ckvCameraActivity.stopVideo();
                binding.ivStartCameraActivity
                    .setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray_F3F3F3)));
            } else {
                isStartRecord = true;
                binding.ckvCameraActivity.captureVideo();
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isStartRecord = false;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            questionModel = (QuestionModel) bundle.getSerializable("question");
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.ckvCameraActivity.start();
    }

    @Override
    public void onPause() {
        binding.ckvCameraActivity.stop();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.d("測試");
        // binding.ckvCameraActivity.(requestCode, permissions, grantResults);
    }

    @Override
    public void onUpload(boolean isSuccess, String url) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("url", url);
        returnIntent.putExtra("questionId", questionModel.getQuestionID());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onEvent(CameraKitEvent cameraKitEvent) {
        LogUtils.d(cameraKitEvent.getMessage());
    }

    @Override
    public void onError(CameraKitError cameraKitError) {
        LogUtils.d(cameraKitError.getMessage());
        binding.ivStartCameraActivity
            .setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed_B70908)));
        isStartRecord = false;
    }

    @Override
    public void onImage(CameraKitImage cameraKitImage) {

    }

    @Override
    public void onVideo(CameraKitVideo cameraKitVideo) {
        binding.ivStartCameraActivity
            .setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorRed_B70908)));
        isStartRecord = false;
        presenter.uploadRecord(cameraKitVideo.getVideoFile(), this);
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
}
