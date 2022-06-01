package com.birdwind.inspire.medical.diary.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.enums.AnswerTypeEnum;
import com.birdwind.inspire.medical.diary.model.MultipleChooseModel;
import com.birdwind.inspire.medical.diary.view.customer.indicatorSeekBar.CustomIndicatorSeekBar;
import com.birdwind.inspire.medical.diary.view.customer.indicatorSeekBar.OnSeekChangeListener;
import com.birdwind.inspire.medical.diary.view.customer.indicatorSeekBar.SeekParams;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class AnswerAdapter extends BaseMultiItemQuickAdapter<MultipleChooseModel, BaseViewHolder> {

    private WeightedAnswerListener weightedAnswerListener;

    private boolean isResult;

    private float totalScore;

    public AnswerAdapter(@Nullable List<MultipleChooseModel> data, WeightedAnswerListener weightedAnswerListener) {
        this(data);
        this.weightedAnswerListener = weightedAnswerListener;
    }

    public AnswerAdapter(@Nullable List<MultipleChooseModel> data) {
        super(data);
        this.setList(data);
        addItemType(AnswerTypeEnum.MULTIPLE, R.layout.item_answer);
        addItemType(AnswerTypeEnum.WEIGHTED, R.layout.item_weight_answer);
        totalScore = 100;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, MultipleChooseModel multipleChooseModel) {
        if (multipleChooseModel.getItemType() == AnswerTypeEnum.MULTIPLE) {
            RadioButton rbAnswerItem = baseViewHolder.getView(R.id.rb_answer_item);
            ImageView ivPicture = baseViewHolder.getView(R.id.iv_answer_item);
            rbAnswerItem.setText(multipleChooseModel.getAnswerText());
            rbAnswerItem.setTag(multipleChooseModel.getChoiceID());
            rbAnswerItem.setChecked(multipleChooseModel.isChoose());
            rbAnswerItem.setEnabled(!isResult);
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
        } else if (multipleChooseModel.getItemType() == AnswerTypeEnum.WEIGHTED) {
            CustomIndicatorSeekBar indicatorSeekBar = baseViewHolder.getView(R.id.isb_answer_item);
            indicatorSeekBar.setEnableValue(totalScore);
            indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
                @Override
                public void onSeeking(SeekParams seekParams) {
                    weightedAnswerListener.updateSeekbar(seekParams.progress, getItemPosition(multipleChooseModel));
                }

                @Override
                public void onStartTrackingTouch(CustomIndicatorSeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(CustomIndicatorSeekBar seekBar) {
                }
            });
            baseViewHolder.setText(R.id.tv_answer_item, multipleChooseModel.getAnswerText());
            if (isResult) {
                ImageButton ibReduce = (ImageButton) baseViewHolder.getView(R.id.ib_reduce_answer_item);
                ImageButton ibPlus = (ImageButton) baseViewHolder.getView(R.id.ib_add_answer_item);
                TextView tvAnswerName = (TextView) baseViewHolder.getView(R.id.tv_answer_item);
                tvAnswerName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGray_BDBDBD));
                indicatorSeekBar.setProgress(multipleChooseModel.getValue());
                ibReduce.setClickable(false);
                ibPlus.setClickable(false);
                indicatorSeekBar.setOnTouchListener((v, event) -> true);
            }
        }
    }

    public void updateData(int choiceID) {
        for (int i = 0; i < getData().size(); i++) {
            MultipleChooseModel multipleChooseModel = getData().get(i);
            RadioButton radioButton = (RadioButton) getViewByPosition(i, R.id.rb_answer_item);
            if (multipleChooseModel.getChoiceID() == choiceID) {
                radioButton.setChecked(true);
            }
        }
    }

    public void setTotalScore(float score) {
        this.totalScore = score;
    }

    public void updateWeightedTotalScore(float score) {
        this.totalScore = score;
        for (int i = 0; i < getData().size(); i++) {
            CustomIndicatorSeekBar customIndicatorSeekBar =
                (CustomIndicatorSeekBar) getViewByPosition(i, R.id.isb_answer_item);
            if (customIndicatorSeekBar != null) {
                customIndicatorSeekBar.setEnableValue(score);
            }
        }
    }

    // public void updateWeightedData(int position, int value) {
    // MultipleChooseModel multipleChooseModel = getData().get(position);
    // IndicatorSeekBar indicatorSeekBar = (IndicatorSeekBar) getViewByPosition(position, R.id.isb_answer_item);
    // ImageButton ibReduce = (ImageButton) getViewByPosition(position, R.id.ib_reduce_answer_item);
    // ImageButton ibPlus = (ImageButton) getViewByPosition(position, R.id.ib_add_answer_item);
    // TextView tvAnswerName = (TextView) getViewByPosition(position, R.id.tv_answer_item);
    // tvAnswerName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGray_BDBDBD));
    // indicatorSeekBar.setProgress(value);
    // ibReduce.setClickable(false);
    // ibPlus.setClickable(false);
    // indicatorSeekBar.setOnTouchListener((v, event) -> true);
    // }

    public void setResult(boolean result) {
        isResult = result;
    }

    public interface WeightedAnswerListener {
        void updateSeekbar(int value, int position);
    }
}
