package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener;
import com.asynctaskcoffee.audiorecorder.worker.MediaPlayListener;
import com.asynctaskcoffee.audiorecorder.worker.Player;
import com.asynctaskcoffee.audiorecorder.worker.Recorder;
import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogRecordVoiceBinding;
import com.birdwind.inspire.medical.diary.model.response.VoiceQuizResponse;
import com.birdwind.inspire.medical.diary.presenter.RecordVoiceDialogPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.dialog.callback.RecordVoiceDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordVoiceDialogView;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RecordVoiceDialog2
    extends AbstractDialog<CommonDialogListener, RecordVoiceDialogPresenter, DialogRecordVoiceBinding>
    implements RecordVoiceDialogView, AudioRecordListener, MediaPlayListener, ProgressRequestBody.UploadCallbacks {

    private boolean isRecording;

    private Recorder recorder;

    private Animation animation;

    private Player player;

    private File file;

    private int questionId;

    private RecordVoiceDialogListener recordVoiceDialogListener;

    public RecordVoiceDialog2(@NonNull @NotNull Context context, RecordVoiceDialogListener recordVoiceDialogListener) {
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
                stopRecordSound();
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
        LogUtils.d("測試");
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

    private void startRecordSound() {
        recorder = new Recorder(this, getContext());
        recorder.setFileName("/" + App.userModel.getUid() + "_" + System.nanoTime() + ".m4a");
        binding.ibtnRecordVoiceDialog.post(() -> {
            binding.ibtnRecordVoiceDialog.setImageTintList(
                ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorRed_B70908)));
        });
        recorder.startRecord();
        isRecording = true;
        binding.ibtnRecordVoiceDialog.startAnimation(animation);
    }

    private void stopRecordSound() {
        try {
            recorder.stopRecording();
        } catch (RuntimeException e) {
            ToastUtils.show(getContext(), "間格時間太小");
        }

        binding.ibtnRecordVoiceDialog.post(() -> {
            binding.ibtnRecordVoiceDialog.setImageTintList(
                ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
        });
        binding.ibtnRecordVoiceDialog.clearAnimation();
        isRecording = false;
    }

    public void initRecord(int questionId) {
        this.questionId = questionId;
        startRecordSound();
    }

    @Override
    public void onGetVoiceQuiz(boolean isSuccess, VoiceQuizResponse.Response response) {

    }

    @Override
    public void onUploadRecord(boolean isSuccess, String url) {
        recorder.reset();
        recordVoiceDialogListener.recordVoiceDone(questionId, url);
        dismiss();
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("上傳", String.valueOf(percentage));
    }

    @Override
    public void onError() {
        LogUtils.d("上傳", "失敗");
        hideLoading();
        dismiss();
    }

    @Override
    public void onFinish() {
        LogUtils.d("上傳", "完成");
        hideLoading();
        dismiss();
    }
}
