package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

public class ImageQuestionBuilder extends QuestionBuilder {

    public ImageQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
    }

    @Override
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.ibtn_drawing_question_item) {
            specialAnswerListener.drawing(getQuestionModel(), getPosition());
        } else if (v.getId() == R.id.ibtn_play_image_question_item
            || v.getId() == R.id.iv_answer_picture_question_item) {
            String url = (String) getValue();
            if (!TextUtils.isEmpty(url)) {
                specialAnswerListener.previewImage(url);
            }
        }
    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf(value));
        getViewByPosition(R.id.ibtn_play_image_question_item).setAlpha(1);
    }

//    @Override
//    public void convertDataToView(String value) {
//        setValue(value);
//        if (getViewByPosition(R.id.ll_question_item) != null) {
//            getViewByPosition(R.id.ll_question_item).setVisibility(View.GONE);
//        }
//
//        if (getViewByPosition(R.id.view_image_underline_drawing_question_item) != null) {
//            getViewByPosition(R.id.view_image_underline_drawing_question_item).setVisibility(View.VISIBLE);
//        }
//        View view = getViewByPosition(R.id.iv_answer_picture_question_item);
//        if (view != null) {
//            view.setVisibility(View.VISIBLE);
//            // view.setTag(R.id.view_tag_answer_url, value);
//            CustomPicasso.getImageLoader(getContext()).load(value).into((ImageView) view);
//        }
//    }
}
