package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;

public class BooleanQuestionBuilder extends QuestionBuilder {

    public BooleanQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
    }

    @Override
    public void onClick(@NonNull View v) {
        initRadioButton();
        if (v.getId() == R.id.rb_true_question_boolean_item) {
            getTrueRadioButton().setChecked(true);
            updateAnswer(true);
        } else if (v.getId() == R.id.rb_false_question_boolean_item) {
            getFalseRadioButton().setChecked(true);
            updateAnswer(false);
        }

    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf((boolean) value));
    }

//    @Override
//    public void convertDataToView(String value) {
//        setValue(value);
//        if (getTrueRadioButton() != null) {
//            getTrueRadioButton().setChecked(value.equals("true"));
//        }
//        if (getFalseRadioButton() != null) {
//            getFalseRadioButton().setChecked(value.equals("false"));
//        }
//    }

    private void initRadioButton() {
        getTrueRadioButton().setChecked(false);
        getFalseRadioButton().setChecked(false);
    }

    private RadioButton getTrueRadioButton() {
        return (RadioButton) getViewByPosition(R.id.rb_true_question_boolean_item);
    }

    private RadioButton getFalseRadioButton() {
        return (RadioButton) getViewByPosition(R.id.rb_false_question_boolean_item);
    }
}
