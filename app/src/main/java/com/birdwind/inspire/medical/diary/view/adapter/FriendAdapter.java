package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.response.PatientResponse;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.jetbrains.annotations.NotNull;

public class FriendAdapter extends BaseQuickAdapter<PatientResponse.Response, BaseViewHolder> {
    public FriendAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PatientResponse.Response response) {
        CircularImageView circularImageView = baseViewHolder.getView(R.id.civ_image_patient_item);

        baseViewHolder.setText(R.id.tv_name_patient_item, response.getName());
        Glide.with(getContext()).load(response.getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar)).into(circularImageView);
    }
}
