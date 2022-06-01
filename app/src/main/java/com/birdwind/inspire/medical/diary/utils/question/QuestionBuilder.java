package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;

public abstract class QuestionBuilder {

    private final Context context;

    protected final int position;

    protected final QuestionAdapter questionAdapter;

    protected QuestionAdapter.AnswerListener answerListener;

    protected Object value;

    protected SpecialAnswerListener specialAnswerListener;

    public QuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        this.context = context;
        this.position = position;
        this.questionAdapter = questionAdapter;
        this.answerListener = answerListener;
        this.specialAnswerListener = this.questionAdapter.getSpecialAnswerListener();
        setValue(null);
    }

    public Context getContext() {
        return context;
    }

    public int getPosition() {
        return position;
    }

    public int getQuestionId() {
        return getQuestionModel().getQuestionID();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public QuestionModel getQuestionModel() {
        return questionAdapter.getItem(position);
    }

    public abstract void onClick(@NonNull View v);

    protected View getViewByPosition(@IdRes int viewId) {
        return questionAdapter.getViewByPosition(position, viewId);
    }

    public abstract void updateAnswer(Object value);
}
