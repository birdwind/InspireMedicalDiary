package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.enums.SettingFunctionEnums;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class SettingItemAdapter extends BaseQuickAdapter<SettingFunctionEnums, BaseViewHolder> {
    public SettingItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SettingFunctionEnums settingFunctionEnums) {
        baseViewHolder.setText(R.id.tv_name_setting_item, getContext().getString(settingFunctionEnums.getNameId()));
    }
}
