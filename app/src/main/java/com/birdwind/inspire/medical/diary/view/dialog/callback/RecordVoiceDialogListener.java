package com.birdwind.inspire.medical.diary.view.dialog.callback;

import com.birdwind.inspire.medical.diary.base.basic.BaseDialogListener;

import java.io.File;

public interface RecordVoiceDialogListener extends BaseDialogListener {

    void recordVoiceDone(int questionId, String recordUrl);
}
