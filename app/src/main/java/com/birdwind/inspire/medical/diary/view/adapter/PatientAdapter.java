package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.appcompat.content.res.AppCompatResources;

import com.birdwind.inspire.medical.diary.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class PatientAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PatientAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_name_patient_item, s);
        baseViewHolder.setImageDrawable(R.id.civ_image_patient_item,
            AppCompatResources.getDrawable(getContext(), R.drawable.example));
    }
}
