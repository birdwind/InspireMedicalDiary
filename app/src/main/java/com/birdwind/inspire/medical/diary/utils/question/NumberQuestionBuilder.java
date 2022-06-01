package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.MultipleChooseModel;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.view.adapter.AnswerAdapter;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

public class NumberQuestionBuilder extends QuestionBuilder {

    public NumberQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
                                 QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
    }

    @Override
    public void onClick(@NonNull View v) {
        try {
            throw new Exception("尚未實作");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf(value));
    }

//    @Override
//    public void convertDataToView(String value) {
//        setValue(value);
//
//    }


}
