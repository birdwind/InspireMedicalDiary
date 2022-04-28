package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class SurveyAdapter extends BaseQuickAdapter<SurveyListResponse.Response, BaseViewHolder> {
    public SurveyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SurveyListResponse.Response response) {
        baseViewHolder.setText(R.id.tv_name_survey_item, response.getSurveyName());
    }
}
