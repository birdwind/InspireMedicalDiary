package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.DateTimeFormatUtils;
import com.birdwind.inspire.medical.diary.model.response.SurveyQuestionnaireListResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class QuestionnaireAdapter extends BaseQuickAdapter<SurveyQuestionnaireListResponse.Response, BaseViewHolder> {
    public QuestionnaireAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SurveyQuestionnaireListResponse.Response response) {
        baseViewHolder.setText(R.id.tv_title_survey_questionnaire_item, response.getSurveyName());
        baseViewHolder.setText(R.id.tv_create_survey_questionnaire_item,
            DateTimeFormatUtils.minuteFormat(response.getSubmitTime()));
    }
}
