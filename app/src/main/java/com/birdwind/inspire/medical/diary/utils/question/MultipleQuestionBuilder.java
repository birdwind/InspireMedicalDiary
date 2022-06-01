package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.view.adapter.AnswerAdapter;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;

public class MultipleQuestionBuilder extends QuestionBuilder {

    public MultipleQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
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
        answerListener.updateAnswer(getQuestionId(), String.valueOf((int) value));
    }

//    @Override
//    public void convertDataToView(String value) {
//        setValue(value);
//        RecyclerView recyclerView = (RecyclerView) getViewByPosition(R.id.rv_answer_question_item);
//        if (recyclerView != null) {
//            AnswerAdapter answerAdapter = (AnswerAdapter) recyclerView.getAdapter();
//            if (answerAdapter != null) {
//                answerAdapter.updateData(Integer.parseInt(value));
//            }
//        }
//    }
}
