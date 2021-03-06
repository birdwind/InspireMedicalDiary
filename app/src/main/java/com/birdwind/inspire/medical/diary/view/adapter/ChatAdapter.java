package com.birdwind.inspire.medical.diary.view.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

public class ChatAdapter extends BaseMultiItemQuickAdapter<ChatResponse.Response, BaseViewHolder> {

    public ChatAdapter() {
        addItemType(ChatResponse.SELF_MESSAGE, R.layout.item_chat_self);
        addItemType(ChatResponse.OTHER_MESSAGE, R.layout.item_chat_other);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ChatResponse.Response response) {
        switch (baseViewHolder.getItemViewType()) {
            case ChatResponse.SELF_MESSAGE:
                baseViewHolder.setText(R.id.tv_message_chat_self_item, response.getContent());
                break;
            case ChatResponse.OTHER_MESSAGE:
                CircularImageView civAvatarChatOtherItem = baseViewHolder.getView(R.id.civ_avatar_chat_other_item);
                baseViewHolder.setText(R.id.tv_name_chat_other_item,
                    response.getFromUID() == 0 ? getContext().getString(R.string.chat_system) : response.getFromName());
                baseViewHolder.setText(R.id.tv_message_chat_other_item, response.getContent());
                if (response.getFromUID() == 0) {
                    CustomPicasso.getImageLoader(getContext()).load(R.drawable.ic_logo).into(civAvatarChatOtherItem);
                } else {
                    // Glide.with(getContext()).load(response.getPhotoUrl())
                    // .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar))
                    // .into(civAvatarChatOtherItem);
                    CustomPicasso.getImageLoader(getContext()).load(response.getPhotoUrl())
                        .placeholder(
                            Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar)))
                        .into(civAvatarChatOtherItem);
                }
                break;
        }
    }
}
