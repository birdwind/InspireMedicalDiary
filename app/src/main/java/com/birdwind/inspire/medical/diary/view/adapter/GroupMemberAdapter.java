package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.response.ChatMemberResponse;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mikhaellopez.circularimageview.CircularImageView;

public class GroupMemberAdapter extends BaseQuickAdapter<ChatMemberResponse.Response, BaseViewHolder> {
    public GroupMemberAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ChatMemberResponse.Response response) {
        CircularImageView civAvatar = baseViewHolder.getView(R.id.civ_avatar_chat_member_item);
        baseViewHolder.setText(R.id.tv_name_chat_member_item, response.getUserName());
        Glide.with(getContext()).load(response.getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar)).into(civAvatar);

        baseViewHolder.setGone(R.id.iv_report_chat_member_item, !response.isHasUnreadReport());
    }
}
