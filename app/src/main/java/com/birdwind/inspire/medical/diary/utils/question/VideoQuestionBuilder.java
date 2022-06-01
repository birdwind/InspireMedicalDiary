package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class VideoQuestionBuilder extends QuestionBuilder {

    public VideoQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.ibtn_record_video_question_item) {
            specialAnswerListener.recordVideo(getQuestionModel(), getPosition());
        } else if (v.getId() == R.id.iv_preview_question_item || v.getId() == R.id.ibtn_play_video_question_item) {
            String url = (String) getValue();
            if (!TextUtils.isEmpty(url)) {
                specialAnswerListener.previewVideo(url);
            }
        }
    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf(value));
        getViewByPosition(R.id.ibtn_play_video_question_item).setAlpha(1);
    }
}
