package com.birdwind.inspire.medical.diary.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.media.MediaRecorder;
import android.os.Build;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener;
import com.asynctaskcoffee.audiorecorder.worker.MediaPlayListener;
import com.asynctaskcoffee.audiorecorder.worker.Player;
import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.FileUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogRecordVoiceBinding;
import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;
import com.birdwind.inspire.medical.diary.presenter.RecordVoiceDialogPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.dialog.callback.RecordVoiceDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordVoiceDialogView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class RecordVoiceDialog
    extends AbstractDialog<CommonDialogListener, RecordVoiceDialogPresenter, DialogRecordVoiceBinding>
    implements RecordVoiceDialogView, AudioRecordListener, MediaPlayListener, ProgressRequestBody.UploadCallbacks {

    private boolean isRecording;

    private MediaRecorder mediaRecorder;

    private Animation animation;

    private Player player;

    private File file;

    private int questionId;

    private RecordVoiceDialogListener recordVoiceDialogListener;

    public RecordVoiceDialog(@NonNull @NotNull Context context, RecordVoiceDialogListener recordVoiceDialogListener) {
        super(context, new CommonDialogListener() {});
        setCancelable(false);
        animation = new AlphaAnimation(1, 0);
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);
        this.recordVoiceDialogListener = recordVoiceDialogListener;
    }

    @Override
    public DialogRecordVoiceBinding getViewBinding() {
        return DialogRecordVoiceBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ibtnRecordVoiceDialog.setOnClickListener(v -> {
            if (isRecording)
                stopRecording();
        });
    }

    @Override
    public void doSomething() {

    }

    @Override
    public RecordVoiceDialogPresenter createPresenter() {
        return new RecordVoiceDialogPresenter(this);
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(listener);
        LogUtils.d("??????");
    }

    @Override
    public void onAudioReady(@Nullable String audioUri) {
        player = new Player(this);
        player.injectMedia(audioUri);
        file = new File(audioUri);
        presenter.uploadRecord(file, this);
    }

    @Override
    public void onReadyForRecord() {

    }

    @Override
    public void onRecordFailed(@Nullable String errorMessage) {
        ToastUtils.show(getContext(), errorMessage);
        binding.ibtnRecordVoiceDialog.post(() -> {
            binding.ibtnRecordVoiceDialog.setImageTintList(
                ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
        });
        binding.ibtnRecordVoiceDialog.clearAnimation();
        dismiss();
    }

    @Override
    public void onStartMedia() {

    }

    @Override
    public void onStopMedia() {

    }

    @SuppressLint("SimpleDateFormat")
    public void startRecording() {
        isRecording = true;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        file = FileUtils.createFile(((AbstractActivity) context).getOutputDirectoryFile("Recording"), ".m4a");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder.setOutputFile(file);
        } else {
            mediaRecorder.setOutputFile("/" + App.userModel.getUid() + "_" + System.nanoTime() + ".m4a");
        }
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mediaRecorder.prepare();
            binding.ibtnRecordVoiceDialog.post(() -> {
                binding.ibtnRecordVoiceDialog.setImageTintList(
                    ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorRed_B70908)));
            });
            binding.ibtnRecordVoiceDialog.startAnimation(animation);
        } catch (IOException e) {
            LogUtils.e("prepare() failed");
        }
        mediaRecorder.start();

    }

    public void stopRecording() {
        mediaRecorder.stop();
        binding.ibtnRecordVoiceDialog.clearAnimation();

        presenter.uploadRecord(file, this);
        mediaRecorder.release();
        mediaRecorder = null;
        isRecording = false;

    }

    // private void startRecordSound() {
    // recorder = new Recorder(this, getContext());
    // recorder.setFileName("/" + App.userModel.getUid() + "_" + System.nanoTime() + ".m4a");
    // binding.ibtnRecordVoiceDialog.post(() -> {
    // binding.ibtnRecordVoiceDialog.setImageTintList(
    // ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorRed_B70908)));
    // });
    // recorder.startRecord();
    // isRecording = true;
    // binding.ibtnRecordVoiceDialog.startAnimation(animation);
    // }
    //
    // private void stopRecordSound() {
    // try {
    // recorder.stopRecording();
    // } catch (RuntimeException e) {
    // ToastUtils.show(getContext(), "??????????????????");
    // }
    //
    // binding.ibtnRecordVoiceDialog.post(() -> {
    // binding.ibtnRecordVoiceDialog.setImageTintList(
    // ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
    // });
    // binding.ibtnRecordVoiceDialog.clearAnimation();
    // isRecording = false;
    // }

    public void initRecord(int questionId) {
        this.questionId = questionId;
        startRecording();
    }

    @Override
    public void onGetVoiceQuiz(boolean isSuccess, VoiceQuizResponse.Response response) {

    }

    @Override
    public void onUploadRecord(boolean isSuccess, String url) {
        // mediaRecorder.reset();
        recordVoiceDialogListener.recordVoiceDone(questionId, url);
        dismiss();
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("??????", String.valueOf(percentage));
    }

    @Override
    public void onError() {
        LogUtils.d("??????", "??????");
        hideLoading();
        dismiss();
    }

    @Override
    public void onFinish() {
        LogUtils.d("??????", "??????");
        hideLoading();
        dismiss();
    }
}
