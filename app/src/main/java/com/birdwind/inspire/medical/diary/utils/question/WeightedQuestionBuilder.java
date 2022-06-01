package com.birdwind.inspire.medical.diary.utils.question;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.request.SurveyAnswerRequest;
import com.birdwind.inspire.medical.diary.view.adapter.AnswerAdapter;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WeightedQuestionBuilder extends QuestionBuilder {

    public WeightedQuestionBuilder(Context context, int position, QuestionAdapter.AnswerListener answerListener,
        QuestionAdapter questionAdapter) {
        super(context, position, answerListener, questionAdapter);
    }

    @Override
    public void onClick(@NonNull View v) {

    }

    @Override
    public void updateAnswer(Object value) {
        setValue(value);
        answerListener.updateAnswer(getQuestionId(), String.valueOf(value));
    }

    // 取得當下答列表並解析為list
    public List<Integer> getWeightedValue(SurveyAnswerRequest surveyAnswerRequest, int answerCount) {
        List<Integer> weightedValue = new ArrayList<>();
        for (SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest : surveyAnswerRequest.getQuestions()) {
            if (questionAnswerRequest.getQuestionID() == getQuestionId()
                && !TextUtils.isEmpty(questionAnswerRequest.getValue())) {
                weightedValue = parseWeightedValue(questionAnswerRequest.getValue());
            }
        }

        // 當List為空時產生答案對應數量
        if (weightedValue.size() < answerCount) {
            for (int i = 0; i < answerCount; i++) {
                weightedValue.add(0);
            }
        }

        return weightedValue;
    }

    public List<Integer> parseWeightedValue(String value) {
        List<Integer> weightedValue = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                weightedValue.add(jsonArray.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weightedValue;
    }
}
