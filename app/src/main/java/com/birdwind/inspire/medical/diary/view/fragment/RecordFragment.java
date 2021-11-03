package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentRecordBinding;
import com.birdwind.inspire.medical.diary.presenter.RecordPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.RecordView;
import com.github.piasy.audioprocessor.AudioProcessor;
import com.github.piasy.rxandroidaudio.StreamAudioPlayer;
import com.github.piasy.rxandroidaudio.StreamAudioRecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecordFragment extends AbstractFragment<RecordPresenter, FragmentRecordBinding> implements RecordView {

    private static final int BUFFER_SIZE = 2048;

    private StreamAudioRecorder streamAudioRecorder;

    private StreamAudioPlayer streamAudioPlayer;

    private AudioProcessor audioProcessor;

    private FileOutputStream fileOutputStream;

    private File recordFile;

    private byte[] buffer;

    private boolean isRecording = false;

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
            if (isRecording) {
                stopRecord();
                isRecording = false;
            } else {
                startRecord();
                isRecording = true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        streamAudioRecorder = StreamAudioRecorder.getInstance();
        streamAudioPlayer = StreamAudioPlayer.getInstance();
        audioProcessor = new AudioProcessor(BUFFER_SIZE);
        buffer = new byte[BUFFER_SIZE];
    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {

    }

    private void startRecord() {
        try {
            recordFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + System.nanoTime() + ".stream.m4a");
            recordFile.createNewFile();
            fileOutputStream = new FileOutputStream(recordFile);
            streamAudioRecorder.start(new StreamAudioRecorder.AudioDataCallback() {
                @Override
                public void onAudioData(byte[] data, int size) {
                    if (fileOutputStream != null) {
                        try {
                            LogUtils.d("錄音AMP", String.valueOf(calcAmp(data, size)));
                            fileOutputStream.write(data, 0, size);
                        } catch (Exception e) {
                            LogUtils.exception(e);
                        }
                    }
                }

                @Override
                public void onError() {
                    binding.ibRecordRecordFragment.post(() -> {
                        showToast("錄音失敗");
                        isRecording = false;
                    });
                }
            });
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    private int calcAmp(byte[] data, int size) {
        int amplitude = 0;
        for (int i = 0; i + 1 < size; i += 2) {
            short value = (short) (((data[i + 1] & 0x000000FF) << 8) + (data[i + 1] & 0x000000FF));
            amplitude += Math.abs(value);
        }
        amplitude /= size / 2;
        return amplitude / 2048;
    }

    private void stopRecord() {
        streamAudioRecorder.stop();
        try {
            fileOutputStream.close();
            fileOutputStream = null;
        } catch (IOException e) {
            LogUtils.exception(e);
        }
    }
}
