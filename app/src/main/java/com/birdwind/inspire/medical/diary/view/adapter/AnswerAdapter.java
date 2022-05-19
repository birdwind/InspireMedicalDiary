package com.birdwind.inspire.medical.diary.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.MultipleChooseModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class AnswerAdapter extends BaseQuickAdapter<MultipleChooseModel, BaseViewHolder> {
    public AnswerAdapter(int layoutResId) {
        super(layoutResId);
    }

    public AnswerAdapter(int layoutResId, @Nullable List<MultipleChooseModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, MultipleChooseModel multipleChooseModel) {
        RadioButton rbAnswerItem = baseViewHolder.getView(R.id.rb_answer_item);
        ImageView ivPicture = baseViewHolder.getView(R.id.iv_answer_item);
        rbAnswerItem.setText(multipleChooseModel.getAnswerText());
        rbAnswerItem.setTag(multipleChooseModel.getChoiceID());
        boolean isAnyMediaHave = false;
        for (MultipleChooseModel item : getData()) {
            if (!TextUtils.isEmpty(item.getMediaLink())) {
                isAnyMediaHave = true;
                break;
            }
        }
        ivPicture.setVisibility(isAnyMediaHave ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(multipleChooseModel.getMediaLink())) {
            Glide.with(getContext()).load(multipleChooseModel.getMediaLink()).into(ivPicture);
        }
    }

    public void updateData(int choiceID) {
        for (int i = 0; i < getData().size(); i++) {
            MultipleChooseModel multipleChooseModel = getData().get(i);
            RadioButton radioButton = (RadioButton) getViewByPosition(i, R.id.rb_answer_item);
            if (multipleChooseModel.getChoiceID() == choiceID) {
                radioButton.setChecked(true);
            }
            radioButton.setEnabled(false);
        }
    }
}
