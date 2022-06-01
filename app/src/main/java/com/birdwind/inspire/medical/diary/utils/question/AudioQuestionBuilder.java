package com.birdwind.inspire.medical.diary.utils.question;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;
import com.birdwind.inspire.medical.diary.view.dialog.RecordVoiceDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.RecordVoiceDialogListener;

public class AudioQuestionBuilder extends QuestionBuilder implements RecordVoiceDialogListener {

    private final RecordVoiceDialog recordVoiceDialog;

    public AudioQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
        recordVoiceDialog = new RecordVoiceDialog(getContext(), this);
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.ibtn_record_audio_question_item) {
            if (((AbstractActivity) getContext()).hasPermission(Manifest.permission.RECORD_AUDIO)) {
                recordVoiceDialog.show();
                recordVoiceDialog.initRecord(getQuestionId());
            }
        } else if (v.getId() == R.id.ibtn_play_audio_question_item || v.getId() == R.id.awv_record_question_item) {
            String url = (String) getValue();
            if (!TextUtils.isEmpty(url)) {
                answerListener.playAudio(getPosition(), url);
            }
        }
    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf(value));
        getViewByPosition(R.id.ibtn_play_audio_question_item).setAlpha(1);
    }

//    @Override
//    public void convertDataToView(String value) {
//        setValue(value);
//        // View audioView = getViewByPosition(R.id.awv_record_question_item);
//        // if (audioView != null) {
//        // audioView.setTag(R.id.view_tag_answer_url, questionAnswerRequest.getValue());
//        // }
//        if (getViewByPosition(R.id.ibtn_record_audio_question_item) != null) {
//            getViewByPosition(R.id.ibtn_record_audio_question_item).setVisibility(View.GONE);
//        }
//
//        if (getViewByPosition(R.id.ibtn_play_audio_question_item) != null) {
//            getViewByPosition(R.id.ibtn_play_audio_question_item).setAlpha(1);
//        }
//    }

    @Override
    public void recordVoiceDone(int questionId, String recordUrl) {
        updateAnswer(recordUrl);
    }
}
