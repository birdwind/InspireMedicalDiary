package com.birdwind.inspire.medical.diary.view.adapter;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import androidx.annotation.NonNull;

public class ChatAdapter extends BaseMultiItemQuickAdapter<ChatResponse.Response, BaseViewHolder> {

    private final int SELF_MESSAGE = 0;

    private final int OTHER_MESSAGE = 1;

    public ChatAdapter() {
        addItemType(SELF_MESSAGE, R.layout.item_chat_self);
        addItemType(OTHER_MESSAGE, R.layout.item_chat_other);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ChatResponse.Response response) {
        switch (baseViewHolder.getItemViewType()) {
            case SELF_MESSAGE:
                break;
            case OTHER_MESSAGE:
                break;
        }
    }
}
