package com.birdwind.inspire.medical.diary.view.activity;

import static com.birdwind.inspire.medical.diary.view.fragment.SurveyFragment.ACTIVITY_RESULT_VIDEO_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.FileUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivityCameraBinding;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.presenter.CameraPresenter;
import com.birdwind.inspire.medical.diary.utils.CameraHelper;
import com.birdwind.inspire.medical.diary.view.viewCallback.CameraView;
import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;
import com.leaf.library.StatusBarUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CameraActivity extends AbstractActivity<CameraPresenter, ActivityCameraBinding>
    implements CameraView, ProgressRequestBody.UploadCallbacks {

    private Animation animation;

    private QuestionModel questionModel;

    private boolean isVideo;

    private boolean isRecording;

    private boolean isShowGrid;

    private boolean isShowFlash;

    private Camera camera;

    private DisplayManager displayManager;

    private ExecutorService cameraExecutor;

    private int displayId = -1;

    private int lensFacing = CameraSelector.LENS_FACING_BACK;

    private Preview preview;

    private ImageCapture imageCapture;

    private ImageAnalysis imageAnalyzer;

    private VideoCapture videoCapture;

    private ProcessCameraProvider cameraProvider;

    private final DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {}

        @Override
        public void onDisplayRemoved(int displayId) {}

        @SuppressLint({"UnsafeOptInUsageError", "RestrictedApi"})
        @Override
        public void onDisplayChanged(int displayId) {
            if (displayId == CameraActivity.this.displayId) {
                int rotation = getWindow().getDecorView().getDisplay().getRotation();
                if (preview != null) {
                    preview.setTargetRotation(rotation);
                }
                if (imageCapture != null) {
                    imageCapture.setTargetRotation(rotation);
                }

                if (videoCapture != null) {
                    videoCapture.setTargetRotation(rotation);
                }

                if (imageAnalyzer != null) {
                    imageAnalyzer.setTargetRotation(rotation);
                }
            }
        }
    };

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
        binding.btnRecordVideo.setOnClickListener(v -> {
            if (!isVideo) {
                takePicture();
            } else {
                recordVideo();
            }
        });
        binding.btnSwitchCamera.setOnClickListener(v -> {
            if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                lensFacing = CameraSelector.LENS_FACING_BACK;
            } else {
                lensFacing = CameraSelector.LENS_FACING_FRONT;
            }
            try {
                bindCameraUseCases();
            } catch (Exception e) {
                // Do nothing
            }
        });

        binding.btnGrid.setOnClickListener(v -> {
            isShowGrid = !isShowGrid;
            binding.btnGrid.setImageDrawable(
                ContextCompat.getDrawable(this, isShowGrid ? R.drawable.ic_grid_off : R.drawable.ic_grid_on));
            binding.groupGridLines.setVisibility(isShowGrid ? View.VISIBLE : View.GONE);
        });

        binding.btnFlash.setOnClickListener(v -> {
            isShowFlash = !isShowFlash;
            binding.btnFlash.setImageDrawable(
                ContextCompat.getDrawable(this, isShowFlash ? R.drawable.ic_flash_off : R.drawable.ic_flash_on));
            if (camera != null) {
                camera.getCameraControl().enableTorch(isShowFlash);
            }
        });

        binding.viewFinder.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果是正面，不用对焦
                if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
                    return false;
                }

                CameraControl cameraControl = camera.getCameraControl();
                DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
                int screenWidth = mDisplayMetrics.widthPixels;
                int screenHeight = mDisplayMetrics.heightPixels;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float sRawX = event.getRawX();
                    float sRawY = event.getRawY();

                    MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(screenWidth, screenHeight);
                    MeteringPoint point = factory.createPoint(sRawX, sRawY);
                    MeteringPoint point2 = factory.createPoint(sRawX + 10, sRawY + 10);
                    FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                        .addPoint(point2, FocusMeteringAction.FLAG_AE) // could have many
                        // auto calling cancelFocusAndMetering in 5 seconds
                        .setAutoCancelDuration(5, TimeUnit.SECONDS).build();

                    ListenableFuture future = cameraControl.startFocusAndMetering(action);
                    future.addListener(() -> {
                        try {
                            FocusMeteringResult result = (FocusMeteringResult) future.get();
                            // 聚焦结果
                        } catch (Exception e) {
                            LogUtils.exception(e);
                        }
                    }, cameraExecutor);
                }
                return true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            questionModel = (QuestionModel) bundle.getSerializable("question");
        }

        animation = new AlphaAnimation(1, 0);
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);

        cameraExecutor = Executors.newSingleThreadExecutor();
        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displayManager.registerDisplayListener(displayListener, null);
        isVideo = true;
        isRecording = false;
        isShowGrid = false;
        isShowFlash = false;
        binding.tvCountDown.setText("");
        initCameraView();
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, App.userModel.getIdentityMainColor());
        binding.viewFinder.post(() -> {
            displayId = binding.viewFinder.getDisplay().getDisplayId();
            setUpCamera();
        });
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.d("測試");
    }

    @Override
    public void onUpload(boolean isSuccess, String url, boolean isVideo) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("url", url);
        returnIntent.putExtra("questionId", questionModel.getQuestionID());
        if (isVideo) {
            setResult(ACTIVITY_RESULT_VIDEO_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        displayManager.unregisterDisplayListener(displayListener);
    }

    private void setUpCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                // 默认打开后置摄像头
                if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                    lensFacing = CameraSelector.LENS_FACING_BACK;
                } else if (cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                    lensFacing = CameraSelector.LENS_FACING_FRONT;
                }

                bindCameraUseCases();
            } catch (CameraInfoUnavailableException | InterruptedException | ExecutionException e) {
                LogUtils.e(e.getMessage());
            }
        }, ContextCompat.getMainExecutor(this));
    }

    /**
     * 更新相机参数
     */
    @SuppressLint({"UnsafeOptInUsageError", "ResourceType"})
    private void bindCameraUseCases() {

        DisplayMetrics metrics = new DisplayMetrics();
        binding.viewFinder.getDisplay().getRealMetrics(metrics);
        int screenAspectRatio = CameraHelper.aspectRatio(metrics.widthPixels, metrics.heightPixels);

        int rotation = binding.viewFinder.getDisplay().getRotation();

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();

        preview = new Preview.Builder().setTargetAspectRatio(screenAspectRatio).setTargetRotation(rotation).build();

        int flashMode = ImageCapture.FLASH_MODE_OFF;
        UseCaseGroup useCaseGroup = null;
        if (!isVideo) {
            imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // 快速拍
                // or
                // 高质量图片
                .setTargetAspectRatio(screenAspectRatio).setTargetRotation(rotation).setFlashMode(flashMode).build();

            imageAnalyzer =
                new ImageAnalysis.Builder().setTargetAspectRatio(screenAspectRatio).setTargetRotation(rotation).build();
            useCaseGroup = new UseCaseGroup.Builder().addUseCase(preview).addUseCase(imageAnalyzer)
                .addUseCase(imageCapture).build();

            // 可设置相机分析器imageAnalyzer.setAnalyzer();
            // Make sure that there are no other use cases bound to CameraX
            cameraProvider.unbindAll();

            try {
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup);
                preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());
            } catch (Exception e) {
                LogUtils.exceptionTAG("Use case binding failed", e);
            }
        } else {
            VideoCaptureConfig videoCaptureConfig = VideoCapture.DEFAULT_CONFIG.getConfig();
            videoCapture = new VideoCapture.Builder().setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(getWindow().getDecorView().getDisplay().getRotation()).build();
            useCaseGroup = new UseCaseGroup.Builder().addUseCase(preview).addUseCase(videoCapture).build();
        }

        // 可设置相机分析器imageAnalyzer.setAnalyzer();
        // Make sure that there are no other use cases bound to CameraX
        cameraProvider.unbindAll();

        try {
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup);
            preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());
        } catch (Exception e) {
            LogUtils.exceptionTAG("Use case binding failed", e);
        }
    }

    private void takePicture() {
        ProgressRequestBody.UploadCallbacks uploadCallbacks = this;
        if (imageCapture != null) {
            // 创建文件放图片数据
            File temp = FileUtils.createFile(getOutputDirectoryFile(Environment.DIRECTORY_DCIM), ".jpg");
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(temp).build();
            imageCapture.takePicture(outputFileOptions, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@Nullable ImageCapture.OutputFileResults outputFileResults) {
                    // insert your code here.
                    LogUtils.e(outputFileResults.getSavedUri().toString());
                    runOnUiThread(() -> {
                        Glide.with(context).load(outputFileResults.getSavedUri())
                            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_no_picture))
                            .into(binding.btnGallery);
                        presenter.uploadRecord(new File(outputFileResults.getSavedUri().getPath()), uploadCallbacks,
                            false);
                    });
                }

                @Override
                public void onError(@NonNull ImageCaptureException error) {
                    // insert your code here.
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Display flash animation to indicate that photo was captured
                binding.cameraxPreviewFrame.postDelayed(() -> {
                    binding.cameraxPreviewFrame.setForeground(new ColorDrawable(Color.BLACK));
                    binding.cameraxPreviewFrame.postDelayed(() -> binding.cameraxPreviewFrame.setForeground(null),
                        CameraHelper.ANIMATION_SLOW_MILLIS);
                }, CameraHelper.ANIMATION_FAST_MILLIS);
            }
        }
    }

    @SuppressLint({"RestrictedApi", "MissingPermission"})
    private void recordVideo() {
        ProgressRequestBody.UploadCallbacks uploadCallbacks = this;
        if (videoCapture != null) {

            if (!isRecording) {
                binding.btnRecordVideo.startAnimation(animation);
                File temp = FileUtils.createFile(getOutputDirectoryFile(Environment.DIRECTORY_DCIM), ".mp4");
                VideoCapture.OutputFileOptions outputFileOptions =
                    new VideoCapture.OutputFileOptions.Builder(temp).build();
                isRecording = true;
                videoCapture.startRecording(outputFileOptions, cameraExecutor, new VideoCapture.OnVideoSavedCallback() {
                    @Override
                    public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                        LogUtils.e(outputFileResults.getSavedUri().toString());
                        runOnUiThread(() -> {
                            presenter.uploadRecord(new File(outputFileResults.getSavedUri().getPath()), uploadCallbacks,
                                true);
                            Glide.with(context).load(outputFileResults.getSavedUri())
                                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_no_picture))
                                .into(binding.btnGallery);

                            binding.btnRecordVideo.clearAnimation();
                        });
                    }

                    @Override
                    public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        if (cause != null){
                            LogUtils.exception(cause);
                        }
                        binding.btnRecordVideo.clearAnimation();
                    }
                });
            } else {
                isRecording = false;
                videoCapture.stopRecording();
            }
        } else {
            LogUtils.e("VideoCapture is null.");
        }
    }

    private void initCameraView() {
        binding.llBtnTimer.setVisibility(isVideo ? View.GONE : View.VISIBLE);
        binding.llBtnHdr.setVisibility(isVideo ? View.GONE : View.VISIBLE);
        binding.llBtnExposure.setVisibility(isVideo ? View.GONE : View.VISIBLE);
    }
}
